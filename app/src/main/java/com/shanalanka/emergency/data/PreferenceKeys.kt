package com.shanalanka.emergency.data

/**
 * Central constants for SharedPreferences keys.
 * Used across SettingsRepository, BatteryMonitorReceiver, ShakeDetectorService, and BootReceiver.
 */
object PreferenceKeys {
    // SharedPreferences file name
    const val PREFS_NAME = "emergency_settings"
    
    // Emergency Settings Keys
    const val KEY_CUSTOM_MESSAGE = "custom_message"
    const val KEY_GPS_HIGH_ACCURACY = "gps_high_accuracy"
    const val KEY_SOUND_ENABLED = "sound_enabled"
    const val KEY_VIBRATION_ENABLED = "vibration_enabled"
    const val KEY_SHAKE_DETECTION_ENABLED = "shake_detection_enabled"
    const val KEY_SHAKE_SENSITIVITY = "shake_sensitivity"
    const val KEY_LOW_BATTERY_ENABLED = "low_battery_enabled"
    const val KEY_BATTERY_THRESHOLD = "battery_threshold"
    
    // Internal State Keys
    const val KEY_BATTERY_ALERT_SENT = "battery_alert_sent"
}
