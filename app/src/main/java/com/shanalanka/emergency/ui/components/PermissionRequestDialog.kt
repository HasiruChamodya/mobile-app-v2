package com.shanalanka.emergency.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Dialog explaining why location permission is needed.
 */
@Composable
fun LocationPermissionDialog(
    onRequestPermission: () -> Unit,
    onDismiss: () -> Unit
) {
    PermissionRationaleDialog(
        icon = Icons.Default.LocationOn,
        title = "Location Permission Required",
        message = "SahanaLanka needs access to your location to send your GPS coordinates to emergency contacts during an emergency alert.",
        onRequest = onRequestPermission,
        onDismiss = onDismiss
    )
}

/**
 * Dialog explaining why SMS permission is needed.
 */
@Composable
fun SmsPermissionDialog(
    onRequestPermission: () -> Unit,
    onDismiss: () -> Unit
) {
    PermissionRationaleDialog(
        icon = Icons.Default.Message,
        title = "SMS Permission Required",
        message = "SahanaLanka needs permission to send SMS messages to alert your emergency contacts during an emergency.",
        onRequest = onRequestPermission,
        onDismiss = onDismiss
    )
}

/**
 * Dialog shown when permission is permanently denied.
 */
@Composable
fun PermissionDeniedDialog(
    permissionType: String,
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text("Permission Required")
        },
        text = {
            Text(
                "$permissionType permission is required for emergency alerts. " +
                "Please enable it in app settings."
            )
        },
        confirmButton = {
            Button(onClick = onOpenSettings) {
                Text("Open Settings")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

/**
 * Generic permission rationale dialog.
 */
@Composable
private fun PermissionRationaleDialog(
    icon: ImageVector,
    title: String,
    message: String,
    onRequest: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(title)
        },
        text = {
            Text(message)
        },
        confirmButton = {
            Button(onClick = onRequest) {
                Text("Grant Permission")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
