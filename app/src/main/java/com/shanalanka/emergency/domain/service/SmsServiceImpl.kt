package com.shanalanka.emergency.domain.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.shanalanka.emergency.data.models.EmergencyContact
import com.shanalanka.emergency.util.LocationFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of SmsService using Android SmsManager.
 * Provides offline-capable SMS sending using cellular network.
 */
class SmsServiceImpl @Inject constructor(
    private val context: Context
) : SmsService {
    
    companion object {
        private const val TAG = "SmsServiceImpl"
    }
    
    override suspend fun sendEmergencyAlert(
        contacts: List<EmergencyContact>,
        latitude: Double,
        longitude: Double
    ): SmsResult = withContext(Dispatchers.IO) {
        
        if (!hasSmsPermission()) {
            return@withContext SmsResult.Error("SMS permission not granted")
        }
        
        if (contacts.isEmpty()) {
            return@withContext SmsResult.Error("No emergency contacts to send to")
        }
        
        val message = formatEmergencyMessage(latitude, longitude)
        
        var sentCount = 0
        var failedCount = 0
        
        val smsManager = context.getSystemService(SmsManager::class.java)
        
        contacts.forEach { contact ->
            try {
                // Check if message needs to be split (longer than 160 chars)
                val parts = smsManager.divideMessage(message)
                
                if (parts.size > 1) {
                    // Send multi-part SMS
                    smsManager.sendMultipartTextMessage(
                        contact.phoneNumber,
                        null,
                        parts,
                        null,
                        null
                    )
                } else {
                    // Send single SMS
                    smsManager.sendTextMessage(
                        contact.phoneNumber,
                        null,
                        message,
                        null,
                        null
                    )
                }
                sentCount++
            } catch (e: Exception) {
                failedCount++
                // Log error but continue sending to other contacts
                Log.e(TAG, "Failed to send SMS to ${contact.name} (${contact.phoneNumber})", e)
            }
        }
        
        if (sentCount == 0) {
            SmsResult.Error("Failed to send SMS to any contact")
        } else {
            SmsResult.Success(sentCount, failedCount)
        }
    }
    
    /**
     * Format the emergency SMS message.
     * Optimized for offline use with both coordinates and map link.
     */
    private fun formatEmergencyMessage(latitude: Double, longitude: Double): String {
        // Format coordinates to 6 decimal places (~0.1 meter accuracy)
        val lat = LocationFormatter.formatLatitude(latitude)
        val lon = LocationFormatter.formatLongitude(longitude)
        
        return """
            🆘 EMERGENCY! I need help.
            
            📍 Location:
            Lat: $lat
            Lon: $lon
            
            🗺️ Map Link:
            https://maps.google.com/?q=$lat,$lon
            
            Sent from SahanaLanka App
        """.trimIndent()
    }
    
    /**
     * Check if SMS permission is granted.
     */
    private fun hasSmsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }
}
