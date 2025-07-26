package cat.naval.atalaya.ui.screens

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class LicenseInfo(val moduleLicense: String?, val moduleLicenseUrl: String)

@Serializable
data class DependencyEntry(
    val moduleName: String,
    val moduleVersion: String,
    val moduleLicenses: List<LicenseInfo>
)

@Serializable
data class LicenseReport(val dependencies: List<DependencyEntry>)

fun loadLicenseReport(context: Context): LicenseReport {
    val withUnknownKeys = Json { ignoreUnknownKeys = true }


    val json = context.assets
        .open("licenses.json")
        .bufferedReader()
        .use { it.readText() }
    return withUnknownKeys.decodeFromString(json)
}

