package cat.naval.atalaya.ui.screens.settings

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.naval.atalaya.CellDataRepository
import cat.naval.atalaya.ui.screens.settings.items.LicensesBottomSheet
import cat.naval.atalaya.ui.screens.settings.items.SettingTileItem
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val clipboardManager = LocalClipboard.current

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 40.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SettingCategory("About")
        SettingTileItem(
            title = "Radio Info",
            icon = Icons.Default.Info,
            onClick = { openRadioInfo(context) }
        )

        SettingTileItem(
            title = "Copy debug data",
            icon = Icons.Default.CopyAll,
            onClick = {
                val clipData = ClipData.newPlainText("raw data", CellDataRepository.rawData())
                val clipEntry = ClipEntry(clipData)
                scope.launch {
                    clipboardManager.setClipEntry(clipEntry)
                }
            }
        )

        SettingTileItem(
            title = "Source code",
            icon = Icons.AutoMirrored.Filled.OpenInNew,
            onClick = {
                uriHandler.openUri("https://github.com/aeri/Atalaya")
            }
        )

        LicensesBottomSheet()
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
