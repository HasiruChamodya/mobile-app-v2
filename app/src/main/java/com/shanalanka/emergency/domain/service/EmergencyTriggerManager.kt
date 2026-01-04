package com.shanalanka.emergency.domain.service

import com.shanalanka.emergency.data.repository.ContactRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages emergency alert triggers from different sources.
 * Coordinates location fetching, contact retrieval, and SMS sending
 * for manual and automatic emergency triggers.
 */
@Singleton
class EmergencyTriggerManager @Inject constructor(
    private val locationService: LocationService,
    private val smsService: SmsService,
    private val contactRepository: ContactRepository
) {
    
    /**
     * Trigger emergency alert from any source.
     * Uses current location for BUTTON and SHAKE, last known for LOW_BATTERY.
     */
    suspend fun triggerEmergencyAlert(triggerType: EmergencyTriggerType) {
        // Get location based on trigger type
        val locationResult = when (triggerType) {
            EmergencyTriggerType.LOW_BATTERY -> {
                // Use last known location for speed (battery dying!)
                locationService.getLastKnownLocation()
            }
            else -> {
                // Try current location, fallback to last known
                val current = locationService.getCurrentLocation()
                if (current is LocationResult.Success) current
                else locationService.getLastKnownLocation()
            }
        }
        
        if (locationResult !is LocationResult.Success) {
            // Could not get location
            return
        }
        
        // Get contacts
        val contacts = contactRepository.getAllContacts().first()
        if (contacts.isEmpty()) return
        
        // Send SMS with appropriate message
        smsService.sendEmergencyAlert(
            contacts = contacts,
            latitude = locationResult.latitude,
            longitude = locationResult.longitude,
            triggerType = triggerType
        )
    }
    
    /**
     * Trigger low battery alert with battery percentage.
     */
    suspend fun triggerLowBatteryAlert(batteryPercent: Int) {
        val locationResult = locationService.getLastKnownLocation()
        
        if (locationResult !is LocationResult.Success) return
        
        val contacts = contactRepository.getAllContacts().first()
        if (contacts.isEmpty()) return
        
        smsService.sendLowBatteryAlert(
            contacts = contacts,
            latitude = locationResult.latitude,
            longitude = locationResult.longitude,
            batteryPercent = batteryPercent
        )
    }
}
