package cat.naval.atalaya

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cat.naval.atalaya.ui.NavigationItem
import cat.naval.atalaya.ui.screens.ExposureScreen
import cat.naval.atalaya.ui.screens.PermissionsRequiredScreen
import cat.naval.atalaya.ui.theme.AtalayaTheme

class MainActivity : ComponentActivity() {
    private val permissionRequestCode = 225

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
            );

        }
        else{
            CellDataRepository.start(this)

        }


        setContent {
            AtalayaTheme {
                ExposureScreen()
            }
        }
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
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {

                    CellDataRepository.start(this)

                    setContent {
                        AtalayaTheme {
                            ExposureScreen()
                        }
                    }
                } else {
                    setContent {
                        AtalayaTheme  {
                            PermissionsRequiredScreen()
                        }
                    }
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

}


data class Message(val author: String, val body: String)

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
fun EntityCard(city: String, status: String, color: Color) {
    Card(
        modifier = Modifier
            .size(150.dp, 150.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.DarkGray)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            /*
            Image(
                painter = painterResource(R.drawable.house_icon),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
             */
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = city,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = status,
                color = color,
                fontSize = 14.sp
            )
        }
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
    intent.setClassName("com.android.settings", "com.android.settings.RadioInfo")
    if (intent.resolveActivity(context.getPackageManager()) != null) {
        context.startActivity(intent)
    }
    intent.setClassName("com.android.phone", "com.android.phone.settings.RadioInfo")
    if (intent.resolveActivity(context.getPackageManager()) != null) {
        print("Failed to open app");
    } else {
        context.startActivity(intent)
    }
}