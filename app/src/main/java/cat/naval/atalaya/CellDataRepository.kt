package cat.naval.atalaya

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import cat.naval.atalaya.base.network.MccMnc
import cat.naval.atalaya.base.network.NetworkData
import cat.naval.atalaya.base.network.RadioState
import cz.mroczis.netmonster.core.db.model.NetworkType
import cz.mroczis.netmonster.core.factory.NetMonsterFactory
import cz.mroczis.netmonster.core.model.cell.ICell
import cz.mroczis.netmonster.core.model.connection.PrimaryConnection
import cz.mroczis.netmonster.core.model.signal.SignalCdma
import cz.mroczis.netmonster.core.model.signal.SignalGsm
import cz.mroczis.netmonster.core.model.signal.SignalLte
import cz.mroczis.netmonster.core.model.signal.SignalNr
import cz.mroczis.netmonster.core.model.signal.SignalTdscdma
import cz.mroczis.netmonster.core.model.signal.SignalWcdma
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader

object CellDataRepository {
    private val _radioStateFlow = MutableStateFlow(RadioState())
    val radioStateFlow: StateFlow<RadioState> = _radioStateFlow.asStateFlow()

    private var isStarted = false
    private const val FILENAME = "mcc-mnc.csv"
    private const val HISTORY = 60

    @SuppressLint("MissingPermission")
    fun start(context: Context) {
        if (isStarted) return
        isStarted = true

        val radioState = RadioState()
        val networks = LinkedHashMap<Int, NetworkData>()

        CoroutineScope(Dispatchers.IO).launch {
            val mccMnc = try {
                context.applicationContext.assets.open(FILENAME).bufferedReader().use {
                    readCsv(it)
                }
            } catch (e: Exception) {
                Log.e("CellDataRepository", "Error reading $FILENAME", e)
                emptyMap()
            }
            val defaultManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            while (true) {
                try {
                    val subscriptions =
                        NetMonsterFactory.getSubscription(context).getActiveSubscriptions()
                    val names = displayNames(context)
                    val netMonster = NetMonsterFactory.get(context)
                    val allCells: List<ICell> = netMonster.getCells()

                    radioState.cells = allCells
                    networks.keys.retainAll(subscriptions.map { it.subscriptionId }.toSet())

                    for (subscription in subscriptions) {
                        val subscriptionId = subscription.subscriptionId
                        val network = networks.getOrPut(subscriptionId) {
                            NetworkData(subscriptionId, subscription.simSlotIndex)
                        }
                        val manager = managerFor(defaultManager, subscriptionId)

                        network.displayName = names[subscriptionId]?.takeUnless { it.isEmpty() }
                            ?: "SIM ${subscription.simSlotIndex + 1}"
                        network.networkType = netMonster.getNetworkType(subscriptionId)
                        network.cell = allCells.firstOrNull {
                            it.subscriptionId == subscriptionId &&
                                    it.connectionStatus == PrimaryConnection()
                        }

                        val simOperator: String = manager.simOperator
                        val networkOperator: String =
                            manager.networkOperator.takeUnless { it.isEmpty() }
                                ?: simOperator
                        val operatorName: String =
                            manager.networkOperatorName.takeUnless { it.isEmpty() }
                                ?: manager.simOperatorName

                        if (!TextUtils.isEmpty(networkOperator)) {
                            network.carrierName = mccMnc[networkOperator]?.name ?: operatorName
                        }

                        network.simCarrierName =
                            if (simOperator.isNotEmpty() && simOperator != networkOperator) {
                                manager.simOperatorName
                            } else ""

                        when (val signal = network.cell?.signal) {
                            is SignalGsm ->
                                network.gsmSignal = (network.gsmSignal + signal).takeLast(HISTORY)

                            is SignalLte ->
                                network.lteSignal = (network.lteSignal + signal).takeLast(HISTORY)

                            is SignalWcdma ->
                                network.wcdmaSignal =
                                    (network.wcdmaSignal + signal).takeLast(HISTORY)

                            is SignalNr ->
                                network.nrSignal = (network.nrSignal + signal).takeLast(HISTORY)

                            is SignalCdma ->
                                network.cdmaSignal = (network.cdmaSignal + signal).takeLast(HISTORY)

                            is SignalTdscdma ->
                                network.tdscdmaSignal =
                                    (network.tdscdmaSignal + signal).takeLast(HISTORY)
                        }
                    }

                    radioState.networks = networks.values.sortedBy { it.slotIndex }.map { it.copy() }
                    radioState.isAirplaneEnabled =
                        radioState.networks.all { it.networkType is NetworkType.Unknown } &&
                                isAirplaneModeOn(context)

                    _radioStateFlow.value = radioState.copy()

                } catch (e: Exception) {
                    Log.e("CellDataRepository", "Error getting cells", e)
                }
                delay(1000L)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun displayNames(context: Context): Map<Int, String> {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) return emptyMap()
        val manager = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE)
                as SubscriptionManager
        return manager.activeSubscriptionInfoList.orEmpty()
            .associate { it.subscriptionId to it.displayName?.toString().orEmpty() }
    }

    private fun managerFor(manager: TelephonyManager, subscriptionId: Int): TelephonyManager =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            manager.createForSubscriptionId(subscriptionId)
        } else manager

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
    }

    private fun readCsv(inputStream: BufferedReader): Map<String, MccMnc> {
        val csvParser = CSVParser(inputStream, CSVFormat.DEFAULT.withDelimiter(';'))
        val carriers = HashMap<String, MccMnc>(4096)
        csvParser.asSequence().drop(1).forEach {
            val plmn = it[2]
            val name = it[7].ifEmpty { it[6] }
            if (name.isNotEmpty() && plmn !in carriers) {
                carriers[plmn] = MccMnc(name, it[5])
            }
        }
        return carriers
    }

    fun rawData(): String {
        return radioStateFlow.value.toString()
    }
}
