package cat.naval.atalaya.ui.screens.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateToNotificationSettings: () -> Unit,
    showFajrImsak: Boolean,
    onShowFajrImsakChanged: (Boolean) -> Unit,
    showDhuha: Boolean,
    onShowDhuhaChanged: (Boolean) -> Unit,
    darkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    use24HourFormat: Boolean,
    onUse24HourFormatChanged: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Settings", style = MaterialTheme.typography.titleSmall)

            Spacer(Modifier.height(16.dp))

            SettingsItem(
                title = "Notification",
                onClick = navigateToNotificationSettings
            )

            SettingsSwitch(
                title = "Show Fajr's Imsak & Syuruk",
                checked = showFajrImsak,
                onCheckedChange = onShowFajrImsakChanged
            )

            SettingsSwitch(
                title = "Show Dhuha Prayer Time",
                checked = showDhuha,
                onCheckedChange = onShowDhuhaChanged
            )

            SettingsSwitch(
                title = "Use Dark Mode",
                checked = darkMode,
                onCheckedChange = onDarkModeChanged
            )

            SettingsSwitch(
                title = "Use 24H Time Format",
                checked = use24HourFormat,
                onCheckedChange = onUse24HourFormatChanged
            )

            Spacer(Modifier.height(32.dp))

            Text("About This App", style = MaterialTheme.typography.titleSmall)

            SettingsItem(title = "App's Info", onClick = { /* TODO */ })
            SettingsItem(title = "Privacy Policy", onClick = { /* TODO */ })
        }
    }
}

@Composable
fun SettingsSwitch(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingsItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.bodyLarge)
    }
}
