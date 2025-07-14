package cat.naval.atalaya.ui.screens.exposure.graph

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.naval.atalaya.NetworkData
import cat.naval.atalaya.ui.screens.exposure.graph.SignalInfo
import cz.mroczis.netmonster.core.model.cell.CellGsm
import cz.mroczis.netmonster.core.model.cell.CellLte
import cz.mroczis.netmonster.core.model.cell.CellNr
import cz.mroczis.netmonster.core.model.cell.CellWcdma
import cz.mroczis.netmonster.core.model.cell.ICell

@Composable
fun SignalSection(networkData: NetworkData, cell: ICell?) {
    Column(modifier = Modifier.padding(horizontal = 15.dp)) {
        Text(
            text = "Signal",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        val signalInfos = when (cell) {
            is CellGsm -> networkData.gsmSignal.toSignalInfoList(
                listOf(
                    Triple("RXL", { it.rssi }, "dBm"),
                    Triple("TA", { it.timingAdvance }, null)
                )
            )
            is CellWcdma -> networkData.wcdmaSignal.toSignalInfoList(
                listOf(
                    Triple("RSSI", { it.rssi }, "dBm"),
                    Triple("RSCP", { it.rscp }, "dBm")
                )
            )
            is CellLte -> networkData.lteSignal.toSignalInfoList(
                listOf(
                    Triple("RSSI", { it.rssi }, "dBm"),
                    Triple("RSRP", { it.rsrp }, "dBm"),
                    Triple("RSRQ", { it.rsrq }, "dB"),
                    Triple("SNR", { it.snr }, "dB")
                )
            )
            is CellNr -> networkData.nrSignal.toSignalInfoList(
                listOf(
                    Triple("SS RSRP", { it.ssRsrp }, "dBm"),
                    Triple("SS RSRQ", { it.ssRsrq }, "dB"),
                    Triple("SS SNR", { it.ssSinr }, "dB")
                )
            )
            else -> emptyList()
        }

        SignalInfoGrid(signalInfos)
    }
}

fun <T> List<T>.toSignalInfoList(
    definitions: List<Triple<String, (T) -> Number?, String?>>
): List<SignalInfo> {
    if (isEmpty()) return emptyList()
    return definitions.mapNotNull { (name, extractor, unit) ->
        val values = this.mapNotNull { extractor(it)?.toFloat() }
        values.takeIf { it.isNotEmpty() }?.let {
            SignalInfo(
                name = name,
                iconDrawable = 1,
                unit = unit,
                currentValue = it.last(),
                values = it,
                tickerName = name
            )
        }
    }
}

@Composable
fun SignalInfoGrid(signalInfos: List<SignalInfo>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        signalInfos.forEach { info ->
            item {
                AssetPerformanceCard(info)
            }
        }
    }
}
