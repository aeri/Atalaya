package cat.naval.atalaya.ui.screens.settings

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    var useDarkMode by remember { mutableStateOf(true) }
    var use24hFormat by remember { mutableStateOf(true) }

    val context = LocalContext.current



    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 40.dp)
            .verticalScroll(rememberScrollState())
    ) {

        SettingCategory("Notification")

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

        SettingCategory("About")


        TextButton(
            onClick = { openRadioInfo(context) },
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            Text(
                "Radio Info",
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        TextButton(
            onClick = { /* Show privacy policy */ },
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            Text(
                "Privacy Policy",
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )
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
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xffffffff),
                checkedTrackColor = Color(0xff4ab178),
                uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            onCheckedChange = onCheckedChange,
            thumbContent = if (checked) {
                {
                    Icon(
                        tint = Color(0xff4ab178),
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
    }
}


fun openRadioInfo(context: Context) {
    val packageManager = context.packageManager

    val intents = listOf(
        Intent(Intent.ACTION_MAIN).apply {
            setClassName("com.android.settings", "com.android.settings.RadioInfo")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        },
        Intent(Intent.ACTION_MAIN).apply {
            setClassName("com.android.phone", "com.android.phone.settings.RadioInfo")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    )

    for (intent in intents) {
        val activities = packageManager.queryIntentActivities(intent, 0)
        if (activities.isNotEmpty()) {
            try {
                context.startActivity(intent)
                return
            } catch (e: Exception) {
                Log.e("RadioInfo", "Cannot open RadioInfo: ${e.message}")
            }
        }
    }

    Log.e("RadioInfo", "Activity not available.")
}
