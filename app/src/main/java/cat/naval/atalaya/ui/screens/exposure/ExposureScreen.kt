package cat.naval.atalaya.ui.screens.exposure

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cat.naval.atalaya.CellDataRepository
import cz.mroczis.netmonster.core.model.connection.PrimaryConnection


@Composable
fun ExposureScreen() {
    val networkData by CellDataRepository.networkDataFlow.collectAsState()
    val cell = networkData.cells.firstOrNull { it.connectionStatus == PrimaryConnection() }

    Column {
        if (networkData.isAirplaneEnabled) {
            AirplaneCard()
            return
        }
        NetworkInfoCard(networkData, cell)
        SignalSection(networkData, cell)

    }
}
