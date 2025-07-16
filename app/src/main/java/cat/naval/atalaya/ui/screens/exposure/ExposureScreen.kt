package cat.naval.atalaya.ui.screens.exposure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cat.naval.atalaya.CellDataRepository
import cz.mroczis.netmonster.core.model.connection.PrimaryConnection


@Composable
fun ExposureScreen() {
    val networkData by CellDataRepository.networkDataFlow.collectAsState()
    val cell = networkData.cells.firstOrNull { it.connectionStatus == PrimaryConnection() }
    
    Column {
        if (!networkData.isAirplaneEnabled) {
            NetworkInfoCard(networkData, cell)
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .verticalScroll(rememberScrollState())
            )
            SignalSection(networkData, cell)
        } else {
            AirplaneCard()
        }
    }
}
