package com.shanalanka.emergency.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shanalanka.emergency.data.models.EmergencySettings
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme

/**
 * Settings screen for configuring emergency alert preferences.
 *
 * @param settings Current emergency settings
 * @param onSettingsChanged Callback when settings are changed
 * @param onTestAlert Callback when test alert button is clicked
 * @param onNavigateBack Callback to navigate back
 * @param onShakeDetectionToggled Callback when shake detection is toggled
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settings: EmergencySettings,
    onSettingsChanged: (EmergencySettings) -> Unit,
    onTestAlert: () -> Unit,
    onNavigateBack: () -> Unit,
    isTestAlertLoading: Boolean = false,
    onShakeDetectionToggled: ((Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Emergency Message Section
            Text(
                text = "Emergency Message",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            OutlinedTextField(
                value = settings.customMessage,
                onValueChange = { newMessage ->
                    onSettingsChanged(settings.copy(customMessage = newMessage))
                },
                label = { Text("Custom Message") },
                supportingText = {
                    Text("Use [GPS_LINK] for location placeholder")
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
            
            Divider()
            
            // GPS Settings Section
            Text(
                text = "GPS Settings",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            SettingSwitch(
                title = "High Accuracy GPS",
                description = "More precise location, uses more battery",
                checked = settings.gpsHighAccuracy,
                onCheckedChange = { checked ->
                    onSettingsChanged(settings.copy(gpsHighAccuracy = checked))
                }
            )
            
            Divider()
            
            // Feedback Settings Section
            Text(
                text = "Feedback",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            SettingSwitch(
                title = "Sound",
                description = "Play sound when alert is sent",
                checked = settings.soundEnabled,
                onCheckedChange = { checked ->
                    onSettingsChanged(settings.copy(soundEnabled = checked))
                }
            )
            
            SettingSwitch(
                title = "Vibration",
                description = "Vibrate when alert is sent",
                checked = settings.vibrationEnabled,
                onCheckedChange = { checked ->
                    onSettingsChanged(settings.copy(vibrationEnabled = checked))
                }
            )
            
            Divider()
            
            // Emergency Triggers Section
            Text(
                text = "Emergency Triggers",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            // Shake Detection
            SettingSwitch(
                title = "Shake Detection",
                description = "Trigger alert by shaking phone 3 times",
                checked = settings.shakeDetectionEnabled,
                onCheckedChange = { checked ->
                    onSettingsChanged(settings.copy(shakeDetectionEnabled = checked))
                    onShakeDetectionToggled?.invoke(checked)
                }
            )
            
            if (settings.shakeDetectionEnabled) {
                // Shake Sensitivity Dropdown
                var expanded by remember { mutableStateOf(false) }
                
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                ) {
                    OutlinedTextField(
                        value = settings.shakeSensitivity.name.lowercase().replaceFirstChar { it.uppercase() },
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Shake Sensitivity") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        com.shanalanka.emergency.data.models.ShakeSensitivity.values().forEach { sensitivity ->
                            DropdownMenuItem(
                                text = { Text(sensitivity.name.lowercase().replaceFirstChar { it.uppercase() }) },
                                onClick = {
                                    onSettingsChanged(settings.copy(shakeSensitivity = sensitivity))
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Low Battery Alert
            SettingSwitch(
                title = "Low Battery Alert",
                description = "Send location when battery is low",
                checked = settings.lowBatteryAlertEnabled,
                onCheckedChange = { checked ->
                    onSettingsChanged(settings.copy(lowBatteryAlertEnabled = checked))
                }
            )
            
            if (settings.lowBatteryAlertEnabled) {
                // Battery Threshold Slider
                Column(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp)
                ) {
                    Text(
                        text = "Battery Threshold: ${settings.batteryThreshold}%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Slider(
                        value = settings.batteryThreshold.toFloat(),
                        onValueChange = { value ->
                            onSettingsChanged(settings.copy(batteryThreshold = value.toInt()))
                        },
                        valueRange = 10f..25f,
                        steps = 2, // 10, 15, 20, 25
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            Divider()
            
            // Test Alert Section
            Text(
                text = "Testing",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Button(
                onClick = onTestAlert,
                enabled = !isTestAlertLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                if (isTestAlertLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sending Test Alert...")
                } else {
                    Text("Send Test Alert")
                }
            }
            
            Text(
                text = "Sends a test message without actual emergency",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Divider()
            
            // App Info Section
            AppInfoCard()
        }
    }
}

/**
 * Reusable switch setting item.
 */
@Composable
private fun SettingSwitch(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

/**
 * App information card.
 */
@Composable
private fun AppInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = "SahanaLanka Emergency",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Version 1.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Emergency alert system for Sri Lanka",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SahanaLankaTheme {
        SettingsScreen(
            settings = EmergencySettings(),
            onSettingsChanged = { },
            onTestAlert = { },
            onNavigateBack = { }
        )
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun SettingsScreenDarkPreview() {
    SahanaLankaTheme(darkTheme = true) {
        SettingsScreen(
            settings = EmergencySettings(
                customMessage = "Help! I need assistance at [GPS_LINK]",
                gpsHighAccuracy = false,
                soundEnabled = false,
                vibrationEnabled = true
            ),
            onSettingsChanged = { },
            onTestAlert = { },
            onNavigateBack = { }
        )
    }
}
