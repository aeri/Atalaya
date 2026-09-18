package cat.naval.atalaya.ui.screens.exposure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import cat.naval.atalaya.CellDataRepository
import cat.naval.atalaya.base.network.NetworkData
import kotlinx.coroutines.launch


@Composable
fun ExposureScreen() {
    val radioState by CellDataRepository.radioStateFlow.collectAsState()

    if (radioState.isAirplaneEnabled) {
        AirplaneCard()
        return
    }

    val networks = radioState.networks
    if (networks.isEmpty()) return

    if (networks.size == 1) {
        NetworkPage(networks.first())
        return
    }

    val pagerState = rememberPagerState { networks.size }
    val scope = rememberCoroutineScope()
    val selected = pagerState.currentPage.coerceAtMost(networks.lastIndex)

    Column {
        HorizontalPager(state = pagerState) { page ->
            NetworkInfoCard(networks[page.coerceAtMost(networks.lastIndex)])
        }
        SimIsland(networks, selected) { page ->
            scope.launch { pagerState.animateScrollToPage(page) }
        }
        SignalSection(networks[selected])
    }
}

@Composable
private fun NetworkPage(network: NetworkData) {
    Column {
        NetworkInfoCard(network)
        SignalSection(network)
    }
}
