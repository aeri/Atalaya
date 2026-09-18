package cat.naval.atalaya.ui.screens.exposure

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SimCard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cat.naval.atalaya.base.network.NetworkData

@Composable
fun SimIsland(networks: List<NetworkData>, selected: Int, onSelect: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        TabRow(
            selectedTabIndex = selected,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            indicator = {},
            divider = {}
        ) {
            networks.forEachIndexed { index, network ->
                Tab(
                    selected = index == selected,
                    onClick = { onSelect(index) },
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (index == selected) {
                                MaterialTheme.colorScheme.surfaceVariant
                            } else Color.Transparent
                        ),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.SimCard,
                            contentDescription = null
                        )
                    },
                    text = {
                        Text(
                            text = network.displayName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }
    }
}
