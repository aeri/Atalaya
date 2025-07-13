package cat.naval.atalaya.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.naval.atalaya.CellDataRepository
import cz.mroczis.netmonster.core.model.cell.CellCdma
import cz.mroczis.netmonster.core.model.cell.CellGsm
import cz.mroczis.netmonster.core.model.cell.CellLte
import cz.mroczis.netmonster.core.model.cell.CellNr
import cz.mroczis.netmonster.core.model.cell.CellTdscdma
import cz.mroczis.netmonster.core.model.cell.CellWcdma
import cz.mroczis.netmonster.core.model.connection.PrimaryConnection
import cz.mroczis.netmonster.core.model.signal.SignalCdma
import cz.mroczis.netmonster.core.model.signal.SignalGsm
import cz.mroczis.netmonster.core.model.signal.SignalLte
import cz.mroczis.netmonster.core.model.signal.SignalNr
import cz.mroczis.netmonster.core.model.signal.SignalTdscdma
import cz.mroczis.netmonster.core.model.signal.SignalWcdma

@Composable
fun ExposureScreen() {
    val networkData by CellDataRepository.networkDataFlow.collectAsState()
    val cell = networkData.cells.firstOrNull { it.connectionStatus == PrimaryConnection() }


    Column {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )

        ) {
            Column(
                modifier = Modifier
                    .padding(0.dp, 48.dp, 0.dp, 0.dp)
                    .height(300.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = networkData.carrierName,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = NetworkHelper.decodeTechnology(cell),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = NetworkHelper.getNetworkType(networkData.networkType),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "${cell?.band?.name} / b${cell?.band?.number}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )

                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (cell) {
                        is CellGsm -> CellGsmInfo(cell)
                        is CellLte -> CellLteInfo(cell)
                        is CellWcdma -> CellWcdmaInfo(cell)
                        is CellNr -> CellNrInfo(cell)
                        is CellCdma -> CellCdma(cell)
                        is CellTdscdma -> CellTdscdma(cell)
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .verticalScroll(rememberScrollState())
        )
        Column(
            modifier = Modifier
                .padding(15.dp, 0.dp, 15.dp, 0.dp)
                .fillMaxSize(),

            ) {
            Text(
                textAlign = TextAlign.Left,
                text = "Signal",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            when (cell) {
                is CellGsm -> GsmSignalInfo(networkData.gsmSignal.toList())
                is CellWcdma -> WcdmaSignalInfo(networkData.wcdmaSignal.toList())
                is CellLte -> LteSignalInfo(networkData.lteSignal.toList())
                is CellNr -> NrSignalInfo(networkData.nrSignal.toList())

            }



        }
    }
}


@Composable
fun GsmSignalInfo(gsmSignal: List<SignalGsm>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "RXL",
                    iconDrawable = 1,
                    unit = "dBm",
                    currentValue = gsmSignal.last().rssi?.toFloat() ?: 0f,
                    values = gsmSignal.mapNotNull { it.rssi?.toFloat() },
                    tickerName = "RSSI",

                    )
            )
        }
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "TA",
                    iconDrawable = 1,
                    currentValue = 1.0f,
                    values = gsmSignal.mapNotNull { it.timingAdvance?.toFloat() },
                    tickerName = "Timing Advance",
                    unit = null
                )
            )
        }
    }
}

@Composable
fun LteSignalInfo(lteSignal: List<SignalLte>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "RSSI",
                    iconDrawable = 1,
                    unit = "dBm",
                    currentValue = lteSignal.last().rssi?.toFloat() ?: 0f,
                    values = lteSignal.mapNotNull { it.rssi?.toFloat() },
                    tickerName = "RSSI",

                    )
            )
        }
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "RSRP",
                    iconDrawable = 1,
                    unit = "dBm",
                    currentValue = lteSignal.last().rsrp?.toFloat() ?: 0f,
                    values = lteSignal.mapNotNull { it.rsrp?.toFloat() },
                    tickerName = "RSSI",

                    )
            )
        }
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "RSRQ",
                    iconDrawable = 1,
                    unit = "dB",
                    currentValue = lteSignal.last().rsrq?.toFloat() ?: 0f,
                    values = lteSignal.mapNotNull { it.rsrq?.toFloat() },
                    tickerName = "RSRQ",

                    )
            )
        }
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "SNR",
                    iconDrawable = 1,
                    unit = "dB",
                    currentValue = lteSignal.last().snr?.toFloat() ?: 0f,
                    values = lteSignal.mapNotNull { it.snr?.toFloat() },
                    tickerName = "SNR",
                )
            )
        }
    }

}


@Composable
fun WcdmaSignalInfo(wcdmaSignal: List<SignalWcdma>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "RSSI",
                    iconDrawable = 1,
                    unit = "dBm",
                    currentValue = wcdmaSignal.last().rssi?.toFloat() ?: 0f,
                    values = wcdmaSignal.mapNotNull { it.rssi?.toFloat() },
                    tickerName = "RSSI",

                    )
            )
        }
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "RSCP",
                    iconDrawable = 1,
                    unit = "dBm",
                    currentValue = wcdmaSignal.last().rscp?.toFloat() ?: 0f,
                    values = wcdmaSignal.mapNotNull { it.rscp?.toFloat() },
                    tickerName = "RSCP",

                    )
            )
        }
    }
}

@Composable
fun NrSignalInfo(nrSignal: List<SignalNr>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "SS RSRP",
                    iconDrawable = 1,
                    unit = "dBm",
                    currentValue = nrSignal.last().ssRsrp?.toFloat() ?: 0f,
                    values = nrSignal.mapNotNull { it.ssRsrp?.toFloat() },
                    tickerName = "SS RSRP",

                    )
            )
        }
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "SS RSRQ",
                    iconDrawable = 1,
                    unit = "dB",
                    currentValue = nrSignal.last().ssRsrq?.toFloat() ?: 0f,
                    values = nrSignal.mapNotNull { it.ssRsrq?.toFloat() },
                    tickerName = "SS RSRQ",

                    )
            )
        }
        item {
            AssetPerformanceCard(
                SignalInfo(
                    name = "SS SNR",
                    iconDrawable = 1,
                    unit = "dB",
                    currentValue = nrSignal.last().ssSinr?.toFloat() ?: 0f,
                    values = nrSignal.mapNotNull { it.ssSinr?.toFloat() },
                    tickerName = "SS SNR",

                    )
            )
        }
    }
}

@Composable
fun CellInfoSection(title: String, value: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
            SelectionContainer {
                Text(
                    text = value,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CellGsmInfo(cell: CellGsm) {
    Column {
        CellInfoSection("CID", cell.cid.toString(), Icons.Default.Fingerprint)
        CellInfoSection("LAC", cell.lac.toString(), Icons.Default.LocationOn)
        CellInfoSection("BSIC", cell.bsic.toString(), Icons.Default.CellTower)
    }
}

@Composable
fun CellLteInfo(cell: CellLte) {
    Row {
        if (cell.aggregatedBands.isNotEmpty()) {
            val bands = "";
            cell.aggregatedBands.forEach {
                bands.plus("+${it.name}")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Text(bands)
        }
        Column {
            CellInfoSection("CI", cell.eci.toString(), Icons.Default.Fingerprint)
            CellInfoSection("eNB", cell.enb.toString(), Icons.Default.Hub)
            CellInfoSection("CID", cell.cid.toString(), Icons.Default.CellTower)
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            CellInfoSection("TAC", cell.tac.toString(), Icons.Default.LocationOn)
            CellInfoSection("PCI", cell.pci.toString(), Icons.Default.CellTower)
            if (cell.bandwidth != null) {
                CellInfoSection("BW", (cell.bandwidth!! / 1000).toString(), Icons.Default.SpaceBar)
            }
        }

    }
}

@Composable
fun CellNrInfo(cell: CellNr) {
    Row {
        Column {
            CellInfoSection("NCI", cell.nci.toString(), Icons.Default.Fingerprint)
            CellInfoSection("TAC", cell.tac.toString(), Icons.Default.CellTower)
            CellInfoSection("PCI", cell.pci.toString(), Icons.Default.CellTower)
        }
        Spacer(modifier = Modifier.width(64.dp))
    }
}

@Composable
fun CellCdma(cell: CellCdma) {
    Row {
        Column {
            CellInfoSection("NID", cell.nid.toString(), Icons.Default.Fingerprint)
            CellInfoSection("BID", cell.bid.toString(), Icons.Default.CellTower)
            CellInfoSection("LAT", cell.lat.toString(), Icons.Default.LocationOn)
            CellInfoSection("LON", cell.lon.toString(), Icons.Default.LocationOn)
        }
        Spacer(modifier = Modifier.width(64.dp))
    }
}

@Composable
fun CellWcdmaInfo(cell: CellWcdma) {
    Row {
        Column {
            CellInfoSection("CI", cell.ci.toString(), Icons.Default.Fingerprint)
            CellInfoSection("CID", cell.cid.toString(), Icons.Default.CellTower)
            CellInfoSection("RNC", cell.rnc.toString(), Icons.Default.RadioButtonChecked)
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            CellInfoSection("LAC", cell.lac.toString(), Icons.Default.LocationOn)
            CellInfoSection("PSC", cell.psc.toString(), Icons.Default.SurroundSound)
        }
    }
}

@Composable
fun CellTdscdma(cell: CellTdscdma) {
    Row {
        Column {
            CellInfoSection("CI", cell.ci.toString(), Icons.Default.Fingerprint)
            CellInfoSection("RNC", cell.rnc.toString(), Icons.Default.RadioButtonChecked)
            CellInfoSection("CID", cell.cid.toString(), Icons.Default.CellTower)
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            CellInfoSection("LAC", cell.lac.toString(), Icons.Default.LocationOn)
            CellInfoSection("CPID", cell.cpid.toString(), Icons.Default.RadioButtonChecked)
        }
    }
}
