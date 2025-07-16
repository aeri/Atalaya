package cat.naval.atalaya.ui.screens.exposure

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
import cat.naval.atalaya.base.signal.GsmRxlSignal
import cat.naval.atalaya.base.signal.GsmTaSignal
import cat.naval.atalaya.base.signal.LteRsrpSignal
import cat.naval.atalaya.base.signal.LteRsrqSignal
import cat.naval.atalaya.base.signal.LteRssiSignal
import cat.naval.atalaya.base.signal.LteSnrSignal
import cat.naval.atalaya.base.signal.NrRsrpSignal
import cat.naval.atalaya.base.signal.NrRsrqSignal
import cat.naval.atalaya.base.signal.NrSnrSignal
import cat.naval.atalaya.base.signal.SignalInfo
import cat.naval.atalaya.base.signal.SignalMeasure
import cat.naval.atalaya.base.signal.WcdmaEcnoSignal
import cat.naval.atalaya.base.signal.WcdmaRscpSignal
import cat.naval.atalaya.base.signal.WcdmaRssiSignal
import cat.naval.atalaya.ui.screens.exposure.graph.AssetPerformanceCard
import cz.mroczis.netmonster.core.model.cell.CellGsm
import cz.mroczis.netmonster.core.model.cell.CellLte
import cz.mroczis.netmonster.core.model.cell.CellNr
import cz.mroczis.netmonster.core.model.cell.CellWcdma
import cz.mroczis.netmonster.core.model.cell.ICell

@Composable
fun SignalSection(networkData: NetworkData, cell: ICell?) {
    Column(modifier = Modifier.padding(horizontal = 15.dp)) {
        Text(
            text = "Signals",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        val signalInfos = when (cell) {
            is CellGsm -> networkData.gsmSignal.toSignalInfoList(
                listOf(
                    GsmRxlSignal { it.rssi },
                    GsmTaSignal { it.timingAdvance },
                )
            )

            is CellWcdma -> networkData.wcdmaSignal.toSignalInfoList(
                listOf(
                    WcdmaRssiSignal { it.rssi },
                    WcdmaRscpSignal { it.rscp },
                    WcdmaEcnoSignal { it.ecno },
                )
            )

            is CellLte -> networkData.lteSignal.toSignalInfoList(
                listOf(
                    LteRssiSignal { it.rssi },
                    LteRsrpSignal { it.rsrp },
                    LteRsrqSignal { it.rsrq },
                    LteSnrSignal { it.snr },
                )
            )

            is CellNr -> networkData.nrSignal.toSignalInfoList(
                listOf(
                    NrRsrpSignal { it.ssRsrp },
                    NrRsrqSignal { it.ssRsrq },
                    NrSnrSignal { it.ssSinr },
                )
            )

            else -> emptyList()
        }

        SignalInfoGrid(signalInfos)
    }
}

fun <T> List<T>.toSignalInfoList(
    measures: List<SignalMeasure<T>>
): List<SignalInfo> {
    if (isEmpty()) return emptyList()
    return measures.mapNotNull { measure ->
        val values = this.mapNotNull { measure.extractor(it)?.toFloat() }
        values.takeIf { it.isNotEmpty() }?.let {
            SignalInfo(
                name = measure.name,
                iconDrawable = measure.iconDrawable,
                unit = measure.unit,
                currentValue = it.last(),
                values = it,
                quality = measure.evaluate(it.lastOrNull()),
                maxValue = measure.maxValue,
                minValue = measure.minValue
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
