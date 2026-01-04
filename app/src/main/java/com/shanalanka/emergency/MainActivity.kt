package com.shanalanka.emergency

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shanalanka.emergency.domain.service.PermissionManager
import com.shanalanka.emergency.ui.components.LocationPermissionDialog
import com.shanalanka.emergency.ui.components.PermissionDeniedDialog
import com.shanalanka.emergency.ui.components.SmsPermissionDialog
import com.shanalanka.emergency.ui.navigation.NavGraph
import com.shanalanka.emergency.ui.navigation.Screen
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main activity for the SahanaLanka Emergency Alert application.
 * 
 * This activity serves as the entry point for the app and sets up
 * the navigation, theme, and permission handling.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var permissionManager: PermissionManager
    
    // Permission dialog states
    private var showLocationPermissionDialog by mutableStateOf(false)
    private var showSmsPermissionDialog by mutableStateOf(false)
    private var showLocationDeniedDialog by mutableStateOf(false)
    private var showSmsDeniedDialog by mutableStateOf(false)
    
    // Permission launchers
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        
        if (!fineLocationGranted && !coarseLocationGranted) {
            if (permissionManager.shouldShowLocationRationale(this)) {
                showLocationPermissionDialog = true
            } else {
                showLocationDeniedDialog = true
            }
        }
    }
    
    private val smsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            if (permissionManager.shouldShowSmsRationale(this)) {
                showSmsPermissionDialog = true
            } else {
                showSmsDeniedDialog = true
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check and request permissions on app start
        lifecycleScope.launch {
            checkAndRequestPermissions()
        }
        
        enableEdgeToEdge()
        setContent {
            SahanaLankaTheme {
                SahanaLankaApp()
                
                // Permission dialogs
                if (showLocationPermissionDialog) {
                    LocationPermissionDialog(
                        onRequestPermission = {
                            showLocationPermissionDialog = false
                            locationPermissionLauncher.launch(PermissionManager.LOCATION_PERMISSIONS)
                        },
                        onDismiss = {
                            showLocationPermissionDialog = false
                        }
                    )
                }
                
                if (showSmsPermissionDialog) {
                    SmsPermissionDialog(
                        onRequestPermission = {
                            showSmsPermissionDialog = false
                            smsPermissionLauncher.launch(android.Manifest.permission.SEND_SMS)
                        },
                        onDismiss = {
                            showSmsPermissionDialog = false
                        }
                    )
                }
                
                if (showLocationDeniedDialog) {
                    PermissionDeniedDialog(
                        permissionType = "Location",
                        onOpenSettings = {
                            showLocationDeniedDialog = false
                            openAppSettings()
                        },
                        onDismiss = {
                            showLocationDeniedDialog = false
                        }
                    )
                }
                
                if (showSmsDeniedDialog) {
                    PermissionDeniedDialog(
                        permissionType = "SMS",
                        onOpenSettings = {
                            showSmsDeniedDialog = false
                            openAppSettings()
                        },
                        onDismiss = {
                            showSmsDeniedDialog = false
                        }
                    )
                }
            }
        }
    }
    
    /**
     * Check and request permissions if not granted.
     */
    private fun checkAndRequestPermissions() {
        if (!permissionManager.hasLocationPermission()) {
            if (permissionManager.shouldShowLocationRationale(this)) {
                showLocationPermissionDialog = true
            } else {
                locationPermissionLauncher.launch(PermissionManager.LOCATION_PERMISSIONS)
            }
        }
        
        if (!permissionManager.hasSmsPermission()) {
            if (permissionManager.shouldShowSmsRationale(this)) {
                showSmsPermissionDialog = true
            } else {
                smsPermissionLauncher.launch(android.Manifest.permission.SEND_SMS)
            }
        }
    }
    
    /**
     * Open app settings for manual permission grant.
     */
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
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
            label = "SOS",
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

