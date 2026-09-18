package cat.naval.atalaya.ui.screens.exposure

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import cat.naval.atalaya.CellDataRepository


@Composable
fun ExposureScreen() {
    val radioState by CellDataRepository.radioStateFlow.collectAsState()

    if (radioState.isAirplaneEnabled) {
        AirplaneCard()
        return
    }

    val networks = radioState.networks
    if (networks.isEmpty()) return

    var selected by rememberSaveable { mutableIntStateOf(0) }
    val index = selected.coerceAtMost(networks.lastIndex)
    val network = networks[index]

    Column {
        NetworkInfoCard(network)
        if (networks.size > 1) {
            SimIsland(networks, index) { selected = it }
        }
        SignalSection(network)
    }
}
