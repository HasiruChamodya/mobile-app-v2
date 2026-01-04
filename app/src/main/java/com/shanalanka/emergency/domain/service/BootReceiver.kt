package com.shanalanka.emergency.domain.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * BroadcastReceiver that restarts emergency services after device boot.
 */
class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return
        
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        
        // Check if shake detection was enabled before reboot - use same key as SettingsRepository
        val settingsPrefs = context.getSharedPreferences("emergency_settings", Context.MODE_PRIVATE)
        val shakeEnabled = settingsPrefs.getBoolean("shake_detection_enabled", false)
        if (shakeEnabled) {
            ShakeDetectorService.start(context)
        }
    }
}
