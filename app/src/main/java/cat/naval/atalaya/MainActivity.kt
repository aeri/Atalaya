package cat.naval.atalaya

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cat.naval.atalaya.ui.NavigationItem
import cat.naval.atalaya.ui.screens.PermissionsRequiredScreen
import cat.naval.atalaya.ui.screens.exposure.ExposureScreen
import cat.naval.atalaya.ui.theme.AtalayaTheme
import java.util.Arrays

class MainActivity : ComponentActivity() {
    private val permissionRequestCode = 225

    fun permissionChecker() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                permissionRequestCode
            )

        } else {
            CellDataRepository.start(this)
            setContent {
                AtalayaTheme {
                    ExposureScreen()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        permissionChecker()
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")

    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        permissionChecker()

    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionRequestCode -> {
                // If request is cancelled, the result arrays are empty.
                if (Arrays.binarySearch(grantResults, PackageManager.PERMISSION_DENIED) >= 0
                ) {
                    setContent {
                        AtalayaTheme {
                            PermissionsRequiredScreen()
                        }
                    }
                } else {
                    CellDataRepository.start(this)
                    setContent {
                        AtalayaTheme {
                            ExposureScreen()
                        }
                    }
                }
                return
            }
        }
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun ExposureScreenPreview() {
    AtalayaTheme {
        ExposureScreen()

    }
}


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            ExposureScreen()
        }
        composable(NavigationItem.Music.route) {
            ExposureScreen()
        }
        composable(NavigationItem.Movies.route) {
            ExposureScreen()
        }
        composable(NavigationItem.Profile.route) {
            ExposureScreen()
        }
    }
}


fun openApp(context: Context) {
    val intent = Intent("android.intent.action.MAIN")
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.setClassName("com.android.settings", "com.android.settings.RadioInfo")
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
    intent.setClassName("com.android.phone", "com.android.phone.settings.RadioInfo")
    if (intent.resolveActivity(context.packageManager) != null) {
        print("Failed to open app")
    } else {
        context.startActivity(intent)
    }
}