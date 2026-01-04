package com.shanalanka.emergency

import android.app.Application
import com.shanalanka.emergency.data.PreferenceKeys
import com.shanalanka.emergency.data.local.ContactEntity
import com.shanalanka.emergency.data.repository.DataRepository
import com.shanalanka.emergency.domain.service.ShakeDetectorService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class SahanaApp : Application() {

    @Inject lateinit var repository: DataRepository // Hilt provides the Repository here

    override fun onCreate() {
        super.onCreate()

        // Default contacts removed - users will add their own contacts
        // No longer pre-populating Police (119) and Ambulance (1990)
        
        // Start shake detection service if it was enabled
        val prefs = getSharedPreferences(PreferenceKeys.PREFS_NAME, MODE_PRIVATE)
        val shakeEnabled = prefs.getBoolean(PreferenceKeys.KEY_SHAKE_DETECTION_ENABLED, false)
        if (shakeEnabled) {
            ShakeDetectorService.start(this)
        }
    }
}