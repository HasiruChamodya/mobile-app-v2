package com.shanalanka.emergency.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shanalanka.emergency.data.models.EmergencyContact
import com.shanalanka.emergency.data.models.EmergencySettings
import com.shanalanka.emergency.ui.screens.ContactsScreen
import com.shanalanka.emergency.ui.screens.EmergencyScreen
import com.shanalanka.emergency.ui.screens.EmergencyGuidesScreen
import com.shanalanka.emergency.ui.screens.GuideDetailScreen
import com.shanalanka.emergency.ui.screens.PoliceDirectoryScreen
import com.shanalanka.emergency.ui.screens.SettingsScreen
import com.shanalanka.emergency.ui.viewmodel.ContactsViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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
            EmergencyScreen(
                onNavigateToContacts = {
                    navController.navigate(Screen.Contacts.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        
        composable(Screen.Police.route) {
            PoliceDirectoryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Guides.route) {
            EmergencyGuidesScreen(
                onGuideClick = { guideId ->
                    navController.navigate(Screen.GuideDetail.createRoute(guideId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.GuideDetail.route,
            arguments = listOf(
                navArgument("guideId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val guideId = backStackEntry.arguments?.getString("guideId") ?: return@composable
            GuideDetailScreen(
                guideId = guideId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Contacts.route) {
            val viewModel: ContactsViewModel = hiltViewModel()
            val contacts by viewModel.contacts. collectAsStateWithLifecycle()

            ContactsScreen(
                contacts = contacts,
                onAddContact = { name, phone ->
                    viewModel.addContact(name, phone)
                },
                onDeleteContact = { contact ->
                    viewModel.deleteContact(contact)
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
