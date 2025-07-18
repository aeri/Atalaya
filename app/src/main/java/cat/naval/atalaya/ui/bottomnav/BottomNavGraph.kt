package cat.naval.atalaya.ui.bottomnav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cat.naval.atalaya.ui.screens.cells.CellListScreen
import cat.naval.atalaya.ui.screens.exposure.ExposureScreen
import cat.naval.atalaya.ui.screens.settings.SettingsScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Monitor.route
    ) {
        composable(route = BottomBarScreen.Monitor.route)
        {
            ExposureScreen()
        }
        composable(route = BottomBarScreen.Cells.route)
        {
            CellListScreen()
        }
        composable(route = BottomBarScreen.About.route)
        {
            SettingsScreen()
        }
    }
}