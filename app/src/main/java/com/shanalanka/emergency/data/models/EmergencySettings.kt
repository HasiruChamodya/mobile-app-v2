package com.shanalanka.emergency.data.models

/**
 * Data model for emergency settings/preferences.
 */
data class EmergencySettings(
    val customMessage: String = DEFAULT_MESSAGE,
    val gpsHighAccuracy: Boolean = true,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
) {
    companion object {
        const val DEFAULT_MESSAGE = "I'm in an emergency. My location is: [GPS_LINK]"
        const val PRESS_AND_HOLD_DURATION_MS = 3000L
    }
}
