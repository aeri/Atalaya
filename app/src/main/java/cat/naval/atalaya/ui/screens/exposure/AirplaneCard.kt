package cat.naval.atalaya.ui.screens.exposure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AirplaneCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )

    ) {
        Column(
            modifier = Modifier
                .padding(0.dp, 48.dp, 0.dp, 30.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "✈ Airplane mode is enabled",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "No network information can be obtained in this mode.",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp,
            )
        }
    }
}