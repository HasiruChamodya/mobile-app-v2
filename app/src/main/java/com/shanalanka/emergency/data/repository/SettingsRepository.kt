package com.shanalanka.emergency.data.repository

import android.content.Context
import android.content.SharedPreferences
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
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _settings = MutableStateFlow(loadSettings())
    val settings: Flow<EmergencySettings> = _settings.asStateFlow()
    
    companion object {
        private const val PREFS_NAME = "emergency_settings"
        private const val KEY_CUSTOM_MESSAGE = "custom_message"
        private const val KEY_GPS_HIGH_ACCURACY = "gps_high_accuracy"
        private const val KEY_SOUND_ENABLED = "sound_enabled"
        private const val KEY_VIBRATION_ENABLED = "vibration_enabled"
    }
    
    /**
     * Load settings from SharedPreferences
     */
    private fun loadSettings(): EmergencySettings {
        return EmergencySettings(
            customMessage = sharedPreferences.getString(
                KEY_CUSTOM_MESSAGE, 
                EmergencySettings.DEFAULT_MESSAGE
            ) ?: EmergencySettings.DEFAULT_MESSAGE,
            gpsHighAccuracy = sharedPreferences.getBoolean(KEY_GPS_HIGH_ACCURACY, true),
            soundEnabled = sharedPreferences.getBoolean(KEY_SOUND_ENABLED, true),
            vibrationEnabled = sharedPreferences.getBoolean(KEY_VIBRATION_ENABLED, true)
        )
    }
    
    /**
     * Save settings to SharedPreferences
     */
    suspend fun saveSettings(settings: EmergencySettings) {
        sharedPreferences.edit().apply {
            putString(KEY_CUSTOM_MESSAGE, settings.customMessage)
            putBoolean(KEY_GPS_HIGH_ACCURACY, settings.gpsHighAccuracy)
            putBoolean(KEY_SOUND_ENABLED, settings.soundEnabled)
            putBoolean(KEY_VIBRATION_ENABLED, settings.vibrationEnabled)
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
