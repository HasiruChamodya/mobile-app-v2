package com.shanalanka.emergency.data.models

/**
 * Data model for emergency settings/preferences.
 */
data class EmergencySettings(
    val customMessage: String = DEFAULT_MESSAGE,
    val gpsHighAccuracy: Boolean = true,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    // New settings for automatic triggers
    val shakeDetectionEnabled: Boolean = false,
    val shakeSensitivity: ShakeSensitivity = ShakeSensitivity.MEDIUM,
    val lowBatteryAlertEnabled: Boolean = false,
    val batteryThreshold: Int = 15
) {
    companion object {
        const val DEFAULT_MESSAGE = "I'm in an emergency. My location is: [GPS_LINK]"
        const val PRESS_AND_HOLD_DURATION_MS = 3000L
    }
}

/**
 * Shake sensitivity levels for shake detection.
 */
enum class ShakeSensitivity {
    LOW,    // Less sensitive, requires stronger shakes
    MEDIUM, // Balanced sensitivity
    HIGH    // More sensitive, triggers with lighter shakes
}
