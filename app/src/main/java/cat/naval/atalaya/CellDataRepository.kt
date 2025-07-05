package cat.naval.atalaya

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import cz.mroczis.netmonster.core.db.model.NetworkType
import cz.mroczis.netmonster.core.factory.NetMonsterFactory
import cz.mroczis.netmonster.core.feature.merge.CellSource
import cz.mroczis.netmonster.core.model.cell.ICell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


object CellDataRepository {

    private val _networkDataFlow = MutableStateFlow(NetworkData())
    val networkDataFlow: StateFlow<NetworkData> = _networkDataFlow.asStateFlow()


    private var isStarted = false

    @SuppressLint("MissingPermission")
    fun start(context: Context) {
        if (isStarted) return
        isStarted = true

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {

                    val networkData = NetworkData()

                    NetMonsterFactory.get(context).apply {
                        val allSources : List<ICell> = getCells() // all sources
                        val subset : List<ICell> = getCells( // subset of available sources
                            CellSource.ALL_CELL_INFO,
                            CellSource.CELL_LOCATION
                        )
                        val networkType : NetworkType = getNetworkType(0)
                        networkData.cells = allSources
                        networkData.networkType = networkType.technology
                    }

                    val manager =
                        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    networkData.carrierName  = manager.networkOperatorName

                    _networkDataFlow.value = networkData


                } catch (e: Exception) {
                    Log.e("CellDataRepository", "Error getting cells", e)
                }
                delay(1000L)
            }
        }
    }

    fun isAirplaneModeOn(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.System.getInt(
                context.contentResolver,
                Settings.System.AIRPLANE_MODE_ON, 0
            ) !== 0
        } else {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON, 0
            ) !== 0
        }
    }
}


