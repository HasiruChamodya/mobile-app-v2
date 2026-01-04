package com.shanalanka.emergency

import android.app.Application
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

        // Seeding happens once when the app starts
        // Since we use OnConflictStrategy.REPLACE in the DAO, this won't create duplicates
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveContactLocally(
                ContactEntity(name = "Police Emergency", phoneNumber = "119", category = "Police", district = "National")
            )
            repository.saveContactLocally(
                ContactEntity(name = "Suwaseriya Ambulance", phoneNumber = "1990", category = "Medical", district = "National")
            )
        }
        
        // Start shake detection service if it was enabled
        val prefs = getSharedPreferences("emergency_settings", MODE_PRIVATE)
        val shakeEnabled = prefs.getBoolean("shake_detection_enabled", false)
        if (shakeEnabled) {
            ShakeDetectorService.start(this)
        }
    }
}