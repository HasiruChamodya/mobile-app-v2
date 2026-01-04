package com.shanalanka.emergency.domain.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.shanalanka.emergency.data.PreferenceKeys

/**
 * BroadcastReceiver that restarts emergency services after device boot.
 */
class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return
        
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        
        // Check if shake detection was enabled before reboot
        val settingsPrefs = context.getSharedPreferences(PreferenceKeys.PREFS_NAME, Context.MODE_PRIVATE)
        val shakeEnabled = settingsPrefs.getBoolean(PreferenceKeys.KEY_SHAKE_DETECTION_ENABLED, false)
        if (shakeEnabled) {
            ShakeDetectorService.start(context)
        }
    }
}
