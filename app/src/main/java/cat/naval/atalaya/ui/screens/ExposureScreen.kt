package cat.naval.atalaya.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun ExposureScreen() {
    val networkData by CellDataRepository.networkDataFlow.collectAsState()
    val cell = networkData.cells.firstOrNull { it.connectionStatus == PrimaryConnection() }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )

        ) {
            Column(
                modifier = Modifier
                    .padding(0.dp, 48.dp, 0.dp, 0.dp)
                    .height(300.dp)
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
                        text = NetMonsterHelper.decodeTechnology(cell),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = cell?.band?.name ?: "N/A",
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Entities",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun CellInfoSection(title: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CellGsmInfo(cell: CellGsm) {
    Column {
        CellInfoSection("CID", cell.cid.toString())
        CellInfoSection("LAC", cell.lac.toString())
        CellInfoSection("BSIC", cell.bsic.toString())
    }
}

@Composable
fun CellLteInfo(cell: CellLte) {
    Row {
        Column {
            CellInfoSection("CI", cell.eci.toString())
            CellInfoSection("eNB", cell.enb.toString())
            CellInfoSection("CID", cell.cid.toString())
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            CellInfoSection("TAC", cell.tac.toString())
            CellInfoSection("PCI", cell.pci.toString())
            if (cell.bandwidth != null){
                CellInfoSection("BW", cell.bandwidth.toString())
            }
        }
        if (cell.aggregatedBands.isNotEmpty()){
            Spacer(modifier = Modifier.width(64.dp))
            Text("CA" )


        }
    }
}

@Composable
fun CellNrInfo(cell: CellNr) {
    Row {
        Column {
            CellInfoSection("NCI", cell.nci.toString())
            CellInfoSection("TAC", cell.tac.toString())
            CellInfoSection("PCI", cell.pci.toString())
        }
        Spacer(modifier = Modifier.width(64.dp))
    }
}

@Composable
fun CellCdma(cell: CellCdma) {
    Row {
        Column {
            CellInfoSection("NID", cell.nid.toString())
            CellInfoSection("BID", cell.bid.toString())
            CellInfoSection("LAT", cell.lat.toString())
            CellInfoSection("LON", cell.lon.toString())
        }
        Spacer(modifier = Modifier.width(64.dp))
    }
}

@Composable
fun CellWcdmaInfo(cell: CellWcdma) {
    Row {
        Column {
            CellInfoSection("CI", cell.ci.toString())
            CellInfoSection("CID", cell.cid.toString())
            CellInfoSection("RNC", cell.rnc.toString())
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            CellInfoSection("LAC", cell.lac.toString())
            CellInfoSection("PSC", cell.psc.toString())
        }
    }
}

@Composable
fun CellTdscdma(cell: CellTdscdma) {
    Row {
        Column {
            CellInfoSection("CI", cell.ci.toString())
            CellInfoSection("RNC", cell.rnc.toString())
            CellInfoSection("CID", cell.cid.toString())
        }
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            CellInfoSection("LAC", cell.lac.toString())
            CellInfoSection("CPID", cell.cpid.toString())
        }
    }
}
