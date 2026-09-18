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
    private val _networkDataFlow = MutableStateFlow(NetworkData())
    val networkDataFlow: StateFlow<NetworkData> = _networkDataFlow.asStateFlow()

    private var isStarted = false
    private const val FILENAME = "mcc-mnc.csv"

    @SuppressLint("MissingPermission")
    fun start(context: Context) {
        if (isStarted) return
        isStarted = true

        val persistentNetworkData = NetworkData()

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
            var subscriptionId: Int
            while (true) {
                try {
                    NetMonsterFactory.getSubscription(context).apply {
                        val active = getActiveSubscriptionIds()
                        subscriptionId = dataSubscriptionId().takeIf { it in active }
                            ?: active.first()
                    }

                    val manager = managerFor(defaultManager, subscriptionId)

                    NetMonsterFactory.get(context).apply {
                        val allSources: List<ICell> = getCells()
                        val networkType: NetworkType = getNetworkType(subscriptionId)

                        persistentNetworkData.cells = allSources
                        persistentNetworkData.networkType = networkType
                        persistentNetworkData.subscriptionId = subscriptionId
                    }

                    if (persistentNetworkData.networkType is NetworkType.Unknown) {
                        if (isAirplaneModeOn(context)) {
                            persistentNetworkData.isAirplaneEnabled = true
                        }
                    } else {
                        persistentNetworkData.isAirplaneEnabled = false
                    }

                    val simOperator: String = manager.simOperator
                    val networkOperator: String =
                        manager.networkOperator.takeUnless { it.isEmpty() }
                            ?: simOperator
                    val operatorName: String =
                        manager.networkOperatorName.takeUnless { it.isEmpty() }
                            ?: manager.simOperatorName

                    if (!TextUtils.isEmpty(networkOperator)) {
                        persistentNetworkData.carrierName =
                            mccMnc[networkOperator]?.name ?: operatorName
                    }

                    persistentNetworkData.simCarrierName =
                        if (simOperator.isNotEmpty() && simOperator != networkOperator) {
                            manager.simOperatorName
                        } else ""

                    val cell = persistentNetworkData.cells.firstOrNull {
                        it.subscriptionId == subscriptionId && it.connectionStatus == PrimaryConnection()
                    }

                    when (val signal = cell?.signal) {
                        is SignalGsm -> persistentNetworkData.gsmSignal += signal
                        is SignalLte -> persistentNetworkData.lteSignal += signal
                        is SignalWcdma -> persistentNetworkData.wcdmaSignal += signal
                        is SignalNr -> persistentNetworkData.nrSignal += signal
                        is SignalCdma -> persistentNetworkData.cdmaSignal += signal
                        is SignalTdscdma -> persistentNetworkData.tdscdmaSignal += signal
                    }

                    _networkDataFlow.value = persistentNetworkData.copy()

                } catch (e: Exception) {
                    Log.e("CellDataRepository", "Error getting cells", e)
                }
                delay(1000L)
            }
        }
    }

    private fun dataSubscriptionId(): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SubscriptionManager.getDefaultDataSubscriptionId()
        } else -1

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
        return networkDataFlow.value.toString()
    }
}
