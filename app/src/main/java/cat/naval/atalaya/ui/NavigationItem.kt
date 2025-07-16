package cat.naval.atalaya.ui

import cat.naval.atalaya.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.outline_settings_input_antenna_24, "Home")
    object Music : NavigationItem("music", R.drawable.outline_settings_input_antenna_24, "Music")
    object Movies : NavigationItem("movies", R.drawable.outline_settings_input_antenna_24, "Movies")
    object Profile :
        NavigationItem("profile", R.drawable.outline_settings_input_antenna_24, "Profile")
}