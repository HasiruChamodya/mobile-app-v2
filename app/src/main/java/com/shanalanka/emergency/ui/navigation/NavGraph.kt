package com.shanalanka.emergency.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shanalanka.emergency.data.models.EmergencyContact
import com.shanalanka.emergency.data.models.EmergencySettings
import com.shanalanka.emergency.ui.screens.ContactsScreen
import com.shanalanka.emergency.ui.screens.EmergencyScreen
import com.shanalanka.emergency.ui.screens.SettingsScreen

/**
 * Navigation graph for the app.
 * Sets up the navigation routes and composable destinations.
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Emergency.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Emergency.route) {
            // TODO: Connect to ViewModel
            EmergencyScreen(
                contactsCount = 0,
                gpsEnabled = true,
                onEmergencyTriggered = {
                    // TODO: Implement emergency alert logic
                },
                onNavigateToContacts = {
                    navController.navigate(Screen.Contacts.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        
        composable(Screen.Contacts.route) {
            // TODO: Connect to ViewModel
            ContactsScreen(
                contacts = emptyList(),
                onAddContact = {
                    // TODO: Implement add contact logic
                },
                onDeleteContact = { contact ->
                    // TODO: Implement delete contact logic
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Settings.route) {
            // TODO: Connect to ViewModel
            SettingsScreen(
                settings = EmergencySettings(),
                onSettingsChanged = { newSettings ->
                    // TODO: Implement settings save logic
                },
                onTestAlert = {
                    // TODO: Implement test alert logic
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
