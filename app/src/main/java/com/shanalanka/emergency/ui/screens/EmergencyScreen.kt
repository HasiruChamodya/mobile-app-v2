package com.shanalanka.emergency.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shanalanka.emergency.ui.components.EmergencyButton
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme

/**
 * Main emergency screen with the emergency button and status indicators.
 *
 * @param contactsCount Number of emergency contacts configured
 * @param gpsEnabled Whether GPS is enabled
 * @param onEmergencyTriggered Callback when emergency button is activated
 * @param onNavigateToContacts Callback to navigate to contacts screen
 * @param onNavigateToSettings Callback to navigate to settings screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen(
    contactsCount: Int,
    gpsEnabled: Boolean,
    onEmergencyTriggered: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "SahanaLanka Emergency",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Status indicators
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                // GPS Status
                StatusChip(
                    icon = Icons.Default.LocationOn,
                    text = if (gpsEnabled) "GPS Active" else "GPS Inactive",
                    isActive = gpsEnabled
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Contacts Status
                StatusChip(
                    icon = Icons.Default.Contacts,
                    text = "$contactsCount Emergency Contact${if (contactsCount != 1) "s" else ""}",
                    isActive = contactsCount > 0
                )
            }
            
            // Center: Emergency button with instructions
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Press and hold for 3 seconds\nto send emergency alert",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                
                EmergencyButton(
                    onEmergencyTriggered = { showConfirmDialog = true },
                    enabled = contactsCount > 0 && gpsEnabled
                )
                
                if (contactsCount == 0 || !gpsEnabled) {
                    Text(
                        text = when {
                            contactsCount == 0 -> "Add emergency contacts to enable"
                            !gpsEnabled -> "Enable GPS to activate"
                            else -> ""
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
            
            // Bottom: Quick action button
            Button(
                onClick = onNavigateToContacts,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .semantics {
                        contentDescription = "Manage emergency contacts"
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Contacts,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Manage Contacts")
            }
        }
    }
    
    // Confirmation dialog
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = {
                Text(
                    text = "Send Emergency Alert?",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = "This will send an SMS with your current location to all $contactsCount emergency contact${if (contactsCount != 1) "s" else ""}.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmDialog = false
                        onEmergencyTriggered()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Send Alert")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

/**
 * Status chip component for displaying GPS and contacts status.
 */
@Composable
private fun StatusChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = if (isActive) {
            MaterialTheme.colorScheme.tertiaryContainer
        } else {
            MaterialTheme.colorScheme.errorContainer
        }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (isActive) {
                    MaterialTheme.colorScheme.onTertiaryContainer
                } else {
                    MaterialTheme.colorScheme.onErrorContainer
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = if (isActive) {
                    MaterialTheme.colorScheme.onTertiaryContainer
                } else {
                    MaterialTheme.colorScheme.onErrorContainer
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmergencyScreenPreview() {
    SahanaLankaTheme {
        EmergencyScreen(
            contactsCount = 3,
            gpsEnabled = true,
            onEmergencyTriggered = { },
            onNavigateToContacts = { },
            onNavigateToSettings = { }
        )
    }
}

@Preview(showBackground = true, name = "No Contacts")
@Composable
fun EmergencyScreenNoContactsPreview() {
    SahanaLankaTheme {
        EmergencyScreen(
            contactsCount = 0,
            gpsEnabled = true,
            onEmergencyTriggered = { },
            onNavigateToContacts = { },
            onNavigateToSettings = { }
        )
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun EmergencyScreenDarkPreview() {
    SahanaLankaTheme(darkTheme = true) {
        EmergencyScreen(
            contactsCount = 2,
            gpsEnabled = false,
            onEmergencyTriggered = { },
            onNavigateToContacts = { },
            onNavigateToSettings = { }
        )
    }
}
