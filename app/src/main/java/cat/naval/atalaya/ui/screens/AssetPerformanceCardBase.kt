package cat.naval.atalaya.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AssetPerformanceCard(
    assetInfo: SignalInfo
) {
    Card(

        colors = CardDefaults.cardColors(containerColor = Color.White)


    ) {
        // Contenedor
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                SignalName(name = assetInfo.name, tickerName = assetInfo.tickerName)
                ValueView(currentValue = assetInfo.currentValue, unit = assetInfo.unit)
            }

            PerformanceChart(
                list = assetInfo.values,
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth()
            )


        }
    }
}