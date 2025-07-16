package cat.naval.atalaya.ui.screens.exposure.graph

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cat.naval.atalaya.base.signal.SignalInfo

@Composable
fun AssetPerformanceCard(
    assetInfo: SignalInfo,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PerformanceChart(
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth(),
                list = assetInfo.values,
                quality = assetInfo.quality,
                defaultMinY = assetInfo.minValue,
                defaultMaxY = assetInfo.maxValue

            )
            Spacer(modifier = Modifier.height(16.dp))
            ValueView(currentValue = assetInfo.currentValue, unit = assetInfo.unit)
            Spacer(modifier = Modifier.width(5.dp))

            SignalName(name = assetInfo.name)


        }
    }
}