package cat.naval.atalaya

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
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

        val mccMnc = context.applicationContext.assets.open(FILENAME).bufferedReader().use {
            readCsv(it)
        }
        val persistentNetworkData = NetworkData()

        CoroutineScope(Dispatchers.IO).launch {
            val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            while (true) {
                try {
                    NetMonsterFactory.get(context).apply {
                        val allSources: List<ICell> = getCells()
                        val networkType: NetworkType = getNetworkType(0)

                        persistentNetworkData.cells = allSources
                        persistentNetworkData.networkType = networkType
                    }

                    if (persistentNetworkData.networkType is NetworkType.Unknown) {
                        if (isAirplaneModeOn(context)) {
                            persistentNetworkData.isAirplaneEnabled = true
                        }
                    } else {
                        persistentNetworkData.isAirplaneEnabled = false
                    }
                    val networkOperator: String = manager.networkOperator

                    if (!TextUtils.isEmpty(networkOperator)) {
                        val mcc = networkOperator.substring(0, 3).toInt()
                        val mnc = networkOperator.substring(3).toInt()
                        persistentNetworkData.carrierName =
                            mccMnc.find { it.mcc == mcc && it.mnc == mnc }?.network
                                ?: manager.networkOperatorName
                    }

                    val cell = persistentNetworkData.cells.firstOrNull {
                        it.connectionStatus == PrimaryConnection()
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

    private fun isAirplaneModeOn(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.System.getInt(
                context.contentResolver,
                Settings.System.AIRPLANE_MODE_ON, 0
            ) != 0
        } else {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON, 0
            ) != 0
        }
    }

    private fun readCsv(inputStream: BufferedReader): List<MccMnc> {
        val csvParser = CSVParser(inputStream, CSVFormat.DEFAULT)
        return csvParser.drop(1).map {
            MccMnc(
                mcc = it[0].toInt(),
                mnc = it[1].toInt(),
                iso = it[2],
                country = it[3],
                countryCode = it[4],
                network = it[5],
            )
        }
    }

    fun rawData(): String {
        return networkDataFlow.value.toString()
    }
}
