package cat.naval.atalaya.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings2Screen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            ListItem(
                headlineContent = { Text("Notification") },
            )
            SettingSwitch("Show Fajr's Imsak & Syuruk")
            SettingSwitch("Show Dhuha Prayer Time")
            SettingSwitch("Use Dark Mode")
            SettingSwitch("Use 24H Time Format")

            Spacer(modifier = Modifier.height(24.dp))
            Text("About This App", style = MaterialTheme.typography.labelLarge)
            ListItem(headlineContent = { Text("App's Info") })
            ListItem(headlineContent = { Text("Privacy Policy") })
        }
    }
}

@Composable
fun SettingSwitch(title: String, initialState: Boolean = true) {
    var checked by remember { mutableStateOf(initialState) }
    ListItem(
        headlineContent = { Text(title) },
        trailingContent = {
            Switch(checked = checked, onCheckedChange = { checked = it })
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen() {
    var notifyBefore by remember { mutableStateOf("10 Mins Before") }
    val options = listOf("15 Mins Before", "10 Mins Before", "5 Mins Before")
    var showDropdown by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification") },

                actions = {
                    TextButton(onClick = { /* Save logic */ }) {
                        Text("DONE")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Configurable Reminder", style = MaterialTheme.typography.labelMedium)

            SettingSwitch("Upcoming Prayer Time")
            Text("Duration", Modifier.padding(start = 16.dp))
            DropdownMenuBox(
                selectedOption = notifyBefore,
                options = options,
                onOptionSelected = { notifyBefore = it }
            )

            SettingSwitch("Prayer Time Ends")
            Text("Duration", Modifier.padding(start = 16.dp))
            DropdownMenuBox(
                selectedOption = notifyBefore,
                options = options,
                onOptionSelected = { notifyBefore = it }
            )

            Text("Prayer Time Notification", style = MaterialTheme.typography.labelMedium)
            ListItem(headlineContent = { Text("Notification Type") }, trailingContent = {
                Text("Default Device Sound")
            })

            Spacer(modifier = Modifier.height(8.dp))
            SettingSwitch("Subuh")
            SettingSwitch("Dhuha")
            SettingSwitch("Dhuhr")
            SettingSwitch("Asr")
            SettingSwitch("Maghrib")
            SettingSwitch("Isha'")
        }
    }
}

@Composable
fun DropdownMenuBox(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        ListItem(
            headlineContent = { Text(selectedOption) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
