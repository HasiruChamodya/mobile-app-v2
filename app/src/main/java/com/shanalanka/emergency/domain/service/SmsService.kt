package com.shanalanka.emergency.domain.service

import com.shanalanka.emergency.data.models.EmergencyContact

/**
 * Service interface for sending SMS messages.
 * Provides offline-capable SMS sending using cellular network.
 */
interface SmsService {
    /**
     * Send emergency alert SMS to multiple contacts.
     * Includes GPS coordinates and Google Maps link.
     * 
     * @param contacts List of emergency contacts to send SMS to
     * @param latitude GPS latitude coordinate
     * @param longitude GPS longitude coordinate
     * @param triggerType Type of trigger that initiated the alert
     * @return SmsResult with success/failure counts
     */
    suspend fun sendEmergencyAlert(
        contacts: List<EmergencyContact>,
        latitude: Double,
        longitude: Double,
        triggerType: EmergencyTriggerType = EmergencyTriggerType.BUTTON
    ): SmsResult
    
    /**
     * Send low battery alert SMS to multiple contacts.
     * Includes last known location and battery percentage.
     * 
     * @param contacts List of emergency contacts to send SMS to
     * @param latitude GPS latitude coordinate
     * @param longitude GPS longitude coordinate
     * @param batteryPercent Current battery percentage
     * @return SmsResult with success/failure counts
     */
    suspend fun sendLowBatteryAlert(
        contacts: List<EmergencyContact>,
        latitude: Double,
        longitude: Double,
        batteryPercent: Int
    ): SmsResult
}

/**
 * Result wrapper for SMS operations.
 */
sealed class SmsResult {
    /**
     * Success result with sent/failed counts.
     * @param sentCount Number of successfully sent messages
     * @param failedCount Number of failed messages
     */
    data class Success(val sentCount: Int, val failedCount: Int) : SmsResult()
    
    /**
     * Error result with message.
     * @param message Error description
     */
    data class Error(val message: String) : SmsResult()
}
