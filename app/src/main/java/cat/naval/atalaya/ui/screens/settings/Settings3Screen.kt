package cat.naval.atalaya.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings3Screen() {
    var showFajr by remember { mutableStateOf(true) }
    var showDhuha by remember { mutableStateOf(true) }
    var useDarkMode by remember { mutableStateOf(true) }
    var use24hFormat by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Settings",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            SettingCategory("Notification")

            SettingSwitchItem(
                title = "Show Fajr’s Imsak & Syuruk",
                checked = showFajr,
                onCheckedChange = { showFajr = it }
            )

            SettingSwitchItem(
                title = "Show Dhuha Prayer Time",
                checked = showDhuha,
                onCheckedChange = { showDhuha = it }
            )

            SettingSwitchItem(
                title = "Use Dark Mode",
                checked = useDarkMode,
                onCheckedChange = { useDarkMode = it }
            )

            SettingSwitchItem(
                title = "Use 24H Time Format",
                checked = use24hFormat,
                onCheckedChange = { use24hFormat = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "About This App",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            TextButton(
                onClick = { /* Show app info */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "App’s Info",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            TextButton(
                onClick = { /* Show privacy policy */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Privacy Policy",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun SettingCategory(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}

@Composable
fun SettingSwitchItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
