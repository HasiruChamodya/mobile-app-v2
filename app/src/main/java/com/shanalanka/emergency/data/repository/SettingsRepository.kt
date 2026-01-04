package com.shanalanka.emergency.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.shanalanka.emergency.data.PreferenceKeys
import com.shanalanka.emergency.data.models.EmergencySettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PreferenceKeys.PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _settings = MutableStateFlow(loadSettings())
    val settings: Flow<EmergencySettings> = _settings.asStateFlow()
    
    /**
     * Load settings from SharedPreferences
     */
    private fun loadSettings(): EmergencySettings {
        val sensitivityName = sharedPreferences.getString(
            PreferenceKeys.KEY_SHAKE_SENSITIVITY,
            com.shanalanka.emergency.data.models.ShakeSensitivity.MEDIUM.name
        ) ?: com.shanalanka.emergency.data.models.ShakeSensitivity.MEDIUM.name
        
        return EmergencySettings(
            customMessage = sharedPreferences.getString(
                PreferenceKeys.KEY_CUSTOM_MESSAGE, 
                EmergencySettings.DEFAULT_MESSAGE
            ) ?: EmergencySettings.DEFAULT_MESSAGE,
            gpsHighAccuracy = sharedPreferences.getBoolean(PreferenceKeys.KEY_GPS_HIGH_ACCURACY, true),
            soundEnabled = sharedPreferences.getBoolean(PreferenceKeys.KEY_SOUND_ENABLED, true),
            vibrationEnabled = sharedPreferences.getBoolean(PreferenceKeys.KEY_VIBRATION_ENABLED, true),
            shakeDetectionEnabled = sharedPreferences.getBoolean(PreferenceKeys.KEY_SHAKE_DETECTION_ENABLED, false),
            shakeSensitivity = try {
                com.shanalanka.emergency.data.models.ShakeSensitivity.valueOf(sensitivityName)
            } catch (e: IllegalArgumentException) {
                com.shanalanka.emergency.data.models.ShakeSensitivity.MEDIUM
            },
            lowBatteryAlertEnabled = sharedPreferences.getBoolean(PreferenceKeys.KEY_LOW_BATTERY_ENABLED, false),
            batteryThreshold = sharedPreferences.getInt(PreferenceKeys.KEY_BATTERY_THRESHOLD, 15)
        )
    }
    
    /**
     * Save settings to SharedPreferences
     */
    suspend fun saveSettings(settings: EmergencySettings) {
        sharedPreferences.edit().apply {
            putString(PreferenceKeys.KEY_CUSTOM_MESSAGE, settings.customMessage)
            putBoolean(PreferenceKeys.KEY_GPS_HIGH_ACCURACY, settings.gpsHighAccuracy)
            putBoolean(PreferenceKeys.KEY_SOUND_ENABLED, settings.soundEnabled)
            putBoolean(PreferenceKeys.KEY_VIBRATION_ENABLED, settings.vibrationEnabled)
            putBoolean(PreferenceKeys.KEY_SHAKE_DETECTION_ENABLED, settings.shakeDetectionEnabled)
            putString(PreferenceKeys.KEY_SHAKE_SENSITIVITY, settings.shakeSensitivity.name)
            putBoolean(PreferenceKeys.KEY_LOW_BATTERY_ENABLED, settings.lowBatteryAlertEnabled)
            putInt(PreferenceKeys.KEY_BATTERY_THRESHOLD, settings.batteryThreshold)
            apply()
        }
        _settings.value = settings
    }
    
    /**
     * Get current settings (synchronous)
     */
    fun getCurrentSettings(): EmergencySettings {
        return _settings.value
    }
}
