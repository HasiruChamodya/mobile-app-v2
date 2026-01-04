package com.shanalanka.emergency.domain.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.shanalanka.emergency.data.PreferenceKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * BroadcastReceiver that monitors battery level and triggers emergency alert
 * when battery drops below configured threshold.
 */
@AndroidEntryPoint
class BatteryMonitorReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var emergencyTriggerManager: EmergencyTriggerManager
    
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    companion object {
        private const val DEFAULT_THRESHOLD = 15
    }
    
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return
        
        if (intent.action != Intent.ACTION_BATTERY_CHANGED) return
        
        // Check if feature is enabled
        val settingsPrefs = context.getSharedPreferences(PreferenceKeys.PREFS_NAME, Context.MODE_PRIVATE)
        val isEnabled = settingsPrefs.getBoolean(PreferenceKeys.KEY_LOW_BATTERY_ENABLED, false)
        if (!isEnabled) return
        
        // Get battery level
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        
        // Validate scale to prevent division by zero
        if (scale <= 0 || level < 0) return
        
        val batteryPct = (level / scale.toFloat() * 100).toInt()
        
        // Get threshold
        val threshold = settingsPrefs.getInt(PreferenceKeys.KEY_BATTERY_THRESHOLD, DEFAULT_THRESHOLD)
        
        // Check if alert already sent
        val alertSent = settingsPrefs.getBoolean(PreferenceKeys.KEY_BATTERY_ALERT_SENT, false)
        
        when {
            batteryPct <= threshold && !alertSent -> {
                // Battery is low and alert not yet sent
                scope.launch {
                    emergencyTriggerManager.triggerLowBatteryAlert(batteryPct)
                    markAlertAsSent(settingsPrefs)
                }
            }
            batteryPct > threshold + 10 && alertSent -> {
                // Battery has recovered (10% hysteresis), reset flag
                resetAlertFlag(settingsPrefs)
            }
        }
    }
    
    private fun markAlertAsSent(prefs: android.content.SharedPreferences) {
        prefs.edit()
            .putBoolean(PreferenceKeys.KEY_BATTERY_ALERT_SENT, true)
            .apply()
    }
    
    private fun resetAlertFlag(prefs: android.content.SharedPreferences) {
        prefs.edit()
            .putBoolean(PreferenceKeys.KEY_BATTERY_ALERT_SENT, false)
            .apply()
    }
}
