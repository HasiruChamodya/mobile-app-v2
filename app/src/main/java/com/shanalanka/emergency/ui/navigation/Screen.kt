package com.shanalanka.emergency.ui.navigation

/**
 * Sealed class defining all navigation routes in the app.
 */
sealed class Screen(val route: String) {
    object Emergency : Screen("emergency")
    object Contacts : Screen("contacts")
    object Settings : Screen("settings")
}
