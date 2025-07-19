package cat.naval.atalaya.ui.screens

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class LicenseInfo(val name: String, val url: String)

@Serializable
data class DependencyEntry(
    val name: String,
    val file: String,
    val licenses: List<LicenseInfo>
)

@Serializable
data class LicenseReport(val dependencies: List<DependencyEntry>)

fun loadLicenseReport(context: Context): LicenseReport {
    val json = context.assets
        .open("dependency-license.json")
        .bufferedReader()
        .use { it.readText() }
    return Json.decodeFromString(json)
}

