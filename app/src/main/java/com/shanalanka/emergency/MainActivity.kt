package com.shanalanka.emergency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shanalanka.emergency.ui.navigation.NavGraph
import com.shanalanka.emergency.ui.navigation.Screen
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the SahanaLanka Emergency Alert application.
 * 
 * This activity serves as the entry point for the app and sets up
 * the navigation and theme.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SahanaLankaTheme {
                SahanaLankaApp()
            }
        }
    }
}

/**
 * Bottom navigation item data class.
 */
data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun SahanaLankaApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    // Bottom navigation items
    val bottomNavItems = listOf(
        BottomNavItem(
            route = Screen.Emergency.route,
            label = "Emergency",
            icon = Icons.Default.Warning
        ),
        BottomNavItem(
            route = Screen.Police.route,
            label = "Police",
            icon = Icons.Default.LocalPolice
        ),
        BottomNavItem(
            route = Screen.Guides.route,
            label = "Guides",
            icon = Icons.Default.MenuBook
        ),
        BottomNavItem(
            route = Screen.Contacts.route,
            label = "Contacts",
            icon = Icons.Default.Contacts
        ),
        BottomNavItem(
            route = Screen.Settings.route,
            label = "Settings",
            icon = Icons.Default.Settings
        )
    )
    
    Scaffold(
        bottomBar = {
            // Only show bottom nav on main screens, not on detail screens
            val shouldShowBottomBar = currentDestination?.route in bottomNavItems.map { it.route }
            
            if (shouldShowBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

