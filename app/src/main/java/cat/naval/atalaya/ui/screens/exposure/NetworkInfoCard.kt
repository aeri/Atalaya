package cat.naval.atalaya.ui.screens.exposure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.naval.atalaya.NetworkData
import cat.naval.atalaya.ui.screens.NetworkHelper
import cat.naval.atalaya.ui.screens.NetworkHelper.Companion.getBandText
import cz.mroczis.netmonster.core.model.cell.CellCdma
import cz.mroczis.netmonster.core.model.cell.CellGsm
import cz.mroczis.netmonster.core.model.cell.CellLte
import cz.mroczis.netmonster.core.model.cell.CellNr
import cz.mroczis.netmonster.core.model.cell.CellTdscdma
import cz.mroczis.netmonster.core.model.cell.CellWcdma
import cz.mroczis.netmonster.core.model.cell.ICell

@Composable
fun NetworkInfoCard(networkData: NetworkData, cell: ICell?) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )

    ) {
        Column(
            modifier = Modifier
                .padding(0.dp, 48.dp, 0.dp, 10.dp)
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
                InfoText(NetworkHelper.getTechnology(networkData.networkType, cell))
                InfoText(NetworkHelper.getNetworkType(networkData.networkType))
                Row {
                    if (cell?.band?.name != null) {
                        InfoText("${cell.band?.name}")
                    }
                    Text("・")
                    if (cell?.band?.name != null) {
                        InfoText(getBandText(cell))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (cell is CellLte && cell.aggregatedBands.isNotEmpty())  {
                val bands = ""
                cell.aggregatedBands.forEach {
                    bands.plus("+${it.name}")
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(bands)
            }
            CellInfoContent(cell)

        }
    }
}

@Composable
fun InfoText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}

@Composable
fun CellInfoContent(cell: ICell?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        when (cell) {
            is CellGsm -> CellGsmInfo(cell)
            is CellLte -> CellLteInfo(cell)
            is CellWcdma -> CellWcdmaInfo(cell)
            is CellNr -> CellNrInfo(cell)
            is CellCdma -> CellCdma(cell)
            is CellTdscdma -> CellTdscdma(cell)
            else -> Text("No cell info available")
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
        Column {
            CellInfoSection("CI", cell.eci.toString(), Icons.Default.Fingerprint)
            CellInfoSection("eNB", cell.enb.toString(), Icons.Default.Hub)
            CellInfoSection("CID", cell.cid.toString(), Icons.Default.Numbers)
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            CellInfoSection("TAC", cell.tac.toString(), Icons.Default.LocationOn)
            CellInfoSection("PCI", cell.pci.toString(), Icons.Default.CellTower)
            if (cell.bandwidth != null) {
                CellInfoSection("BW", "${cell.bandwidth!! / 1000} MHz", Icons.Default.SpaceBar)
            }
        }

    }
}

@Composable
fun CellNrInfo(cell: CellNr) {
    Row {
        Column {
            CellInfoSection("NCI", cell.nci.toString(), Icons.Default.Fingerprint)
            CellInfoSection("TAC", cell.tac.toString(), Icons.Default.LocationOn)
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
            CellInfoSection("BID", cell.bid.toString(), Icons.Default.Numbers)
        }
        Spacer(modifier = Modifier.width(64.dp))
    }
}

@Composable
fun CellWcdmaInfo(cell: CellWcdma) {
    Row {
        Column {
            CellInfoSection("CI", cell.ci.toString(), Icons.Default.Fingerprint)
            CellInfoSection("CID", cell.cid.toString(), Icons.Default.Numbers)
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
            CellInfoSection("CID", cell.cid.toString(), Icons.Default.Numbers)
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            CellInfoSection("LAC", cell.lac.toString(), Icons.Default.LocationOn)
            CellInfoSection("CPID", cell.cpid.toString(), Icons.Default.RadioButtonChecked)
        }
    }
}