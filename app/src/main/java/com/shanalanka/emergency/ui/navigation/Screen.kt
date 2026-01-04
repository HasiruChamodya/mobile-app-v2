package com.shanalanka.emergency.ui.navigation

/**
 * Sealed class defining all navigation routes in the app.
 */
sealed class Screen(val route: String) {
    object Emergency : Screen("emergency")
    object Police : Screen("police")
    object Guides : Screen("guides")
    object GuideDetail : Screen("guide_detail/{guideId}") {
        fun createRoute(guideId: String) = "guide_detail/$guideId"
    }
    object Contacts : Screen("contacts")
    object Settings : Screen("settings")
}
