package cat.naval.atalaya.ui.screens.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cat.naval.atalaya.CellDataRepository
import cat.naval.atalaya.base.signal.GsmRxlSignal
import cat.naval.atalaya.base.signal.GsmTaSignal
import cat.naval.atalaya.base.signal.LteRsrpSignal
import cat.naval.atalaya.base.signal.LteRsrqSignal
import cat.naval.atalaya.base.signal.LteRssiSignal
import cat.naval.atalaya.base.signal.LteSnrSignal
import cat.naval.atalaya.base.signal.NrRsrpSignal
import cat.naval.atalaya.base.signal.NrRsrqSignal
import cat.naval.atalaya.base.signal.NrSnrSignal
import cat.naval.atalaya.base.signal.SignalMeasure
import cat.naval.atalaya.base.signal.WcdmaEcnoSignal
import cat.naval.atalaya.base.signal.WcdmaRscpSignal
import cat.naval.atalaya.base.signal.WcdmaRssiSignal
import cat.naval.atalaya.ui.screens.NetworkHelper.Companion.getBandText
import cat.naval.atalaya.ui.screens.exposure.AirplaneCard
import cz.mroczis.netmonster.core.model.cell.CellCdma
import cz.mroczis.netmonster.core.model.cell.CellGsm
import cz.mroczis.netmonster.core.model.cell.CellLte
import cz.mroczis.netmonster.core.model.cell.CellNr
import cz.mroczis.netmonster.core.model.cell.CellTdscdma
import cz.mroczis.netmonster.core.model.cell.CellWcdma
import cz.mroczis.netmonster.core.model.cell.ICell

@Composable
fun CellListScreen() {
    val networkData by CellDataRepository.networkDataFlow.collectAsState()

    if (networkData.isAirplaneEnabled) {
        AirplaneCard()
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, start = 8.dp, end = 8.dp)

    ) {

        items(networkData.cells) { cell ->
            LocationItemView(
                cell, modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
fun LocationItemView(cell: ICell, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Transparent)
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            when (cell) {
                is CellGsm -> CellGsmRow(cell)
                is CellLte -> CellLteRow(cell)
                is CellWcdma -> CellWcdmaRow(cell)
                is CellNr -> CellNrRow(cell)
                is CellCdma -> CellCdmaRow(cell)
                is CellTdscdma -> CellTdscdmaRow(cell)
                else -> Text("No cell info available")
            }


        }
    }
}

@Composable
fun CellTdscdmaRow(cell: CellTdscdma) {
    cell.cpid?.let { id ->
        Text(
            text = id.toString(),
            style = MaterialTheme.typography.titleMedium
        )
    }
    cell.band?.name?.let { name ->
        Text(
            text = "$name・${getBandText(cell)}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = listOf(
                WcdmaRssiSignal<Int> { cell.signal.rssi },
                WcdmaRscpSignal<Int> { cell.signal.rscp },
            ).joinValues(0),
            style = MaterialTheme.typography.bodySmall
        )

    }
}

@Composable
fun CellCdmaRow(cell: CellCdma) {
    cell.bid?.let { id ->
        Text(
            text = id.toString(),
            style = MaterialTheme.typography.titleMedium
        )
    }
    cell.band?.name?.let { name ->
        Text(
            text = "$name・${getBandText(cell)}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = listOf(
                WcdmaRssiSignal<Int> { cell.signal.cdmaRssi },
                WcdmaRssiSignal<Int> { cell.signal.cdmaEcio },
            ).joinValues(0),
            style = MaterialTheme.typography.bodySmall
        )

    }
}

@Composable
fun CellNrRow(cell: CellNr) {
    cell.pci?.let { id ->
        Text(
            text = id.toString(),
            style = MaterialTheme.typography.titleMedium
        )
    }
    cell.band?.name?.let { name ->
        Text(
            text = "$name・${getBandText(cell)}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = listOf(
                NrRsrpSignal<Int> { cell.signal.ssRsrp },
                NrRsrqSignal { cell.signal.ssRsrq },
                NrSnrSignal { cell.signal.ssSinr },
            ).joinValues(0),
            style = MaterialTheme.typography.bodySmall
        )

    }
}

@Composable
fun CellGsmRow(cell: CellGsm) {
    Text(
        text = "${cell.cid} ${cell.lac} ${cell.bsic}",
        style = MaterialTheme.typography.titleMedium
    )
    Text(
        text = "${cell.band?.name}・${getBandText(cell)}",
        style = MaterialTheme.typography.bodyMedium
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = listOf(
                GsmRxlSignal<Int> { cell.signal.rssi },
                GsmTaSignal<Int> { cell.signal.timingAdvance },
            ).joinValues(0),
            style = MaterialTheme.typography.bodySmall
        )

    }
}

@Composable
fun CellLteRow(cell: CellLte) {
    cell.pci?.let { id ->
        Text(
            text = id.toString(),
            style = MaterialTheme.typography.titleMedium
        )
    }
    cell.band?.name?.let { name ->
        Text(
            text = "$name・${getBandText(cell)}",
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = listOf(
                LteRssiSignal<Int> { cell.signal.rssi },
                LteRsrpSignal { cell.signal.rsrp },
                LteRsrqSignal { cell.signal.rsrq },
                LteSnrSignal { cell.signal.snr },
            ).joinValues(0),
            style = MaterialTheme.typography.bodySmall
        )

    }
}


@Composable
fun CellWcdmaRow(cell: CellWcdma) {
    cell.psc?.let { id ->
        Text(
            text = id.toString(),
            style = MaterialTheme.typography.titleMedium
        )
    }
    cell.band?.name?.let { name ->
        Text(
            text = "$name・${getBandText(cell)}",
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = listOf(
                WcdmaRssiSignal<Int> { cell.signal.rssi },
                WcdmaRscpSignal<Int> { cell.signal.rscp },
                WcdmaEcnoSignal<Int> { cell.signal.ecno },
            ).joinValues(0),
            style = MaterialTheme.typography.bodySmall
        )

    }
}

fun <T> List<SignalMeasure<T>>.joinValues(target: T): String =
    mapNotNull { measure ->
        val value = measure.extractor(target)
        if (value != null) "${measure.name}: $value" else null
    }.joinToString(" / ")
