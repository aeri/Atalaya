package cat.naval.atalaya.ui.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val iconFocused: ImageVector
) {

    object Monitor : BottomBarScreen(
        route = "monitor",
        title = "Monitor",
        icon = Icons.Default.MonitorHeart,
        iconFocused = Icons.Default.MonitorHeart,
    )

    object Cells : BottomBarScreen(
        route = "cells",
        title = "Cells",
        icon = Icons.Default.CellTower,
        iconFocused = Icons.Default.CellTower
    )

    object About : BottomBarScreen(
        route = "about",
        title = "About",
        icon = Icons.Default.Icecream,
        iconFocused = Icons.Default.Icecream,

        )

}
