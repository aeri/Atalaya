package cat.naval.atalaya

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
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

    private val _cellDataFlow = MutableStateFlow<List<ICell>>(emptyList())
    val cellDataFlow: StateFlow<List<ICell>> = _cellDataFlow.asStateFlow()

    private var isStarted = false

    @SuppressLint("MissingPermission")
    fun start(context: Context) {
        if (isStarted) return
        isStarted = true

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {

                    NetMonsterFactory.get(context).apply {
                        val allSources : List<ICell> = getCells() // all sources
                        val subset : List<ICell> = getCells( // subset of available sources
                            CellSource.ALL_CELL_INFO,
                            CellSource.CELL_LOCATION
                        )
                        _cellDataFlow.value = allSources


                    }


                } catch (e: Exception) {
                    Log.e("CellDataRepository", "Error getting cells", e)
                }
                delay(1000L)
            }
        }
    }
}


