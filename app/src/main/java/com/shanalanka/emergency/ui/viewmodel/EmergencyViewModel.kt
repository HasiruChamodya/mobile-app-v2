package com.shanalanka.emergency.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanalanka.emergency.domain.service.*
import com.shanalanka.emergency.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Emergency screen.
 * Manages emergency alert functionality, GPS status, and permissions.
 */
@HiltViewModel
class EmergencyViewModel @Inject constructor(
    private val locationService: LocationService,
    private val smsService: SmsService,
    private val contactRepository: ContactRepository,
    private val permissionManager: PermissionManager
) : ViewModel() {
    
    // UI State
    private val _emergencyState = MutableStateFlow<EmergencyState>(EmergencyState.Idle)
    val emergencyState: StateFlow<EmergencyState> = _emergencyState.asStateFlow()
    
    private val _gpsEnabled = MutableStateFlow(false)
    val gpsEnabled: StateFlow<Boolean> = _gpsEnabled.asStateFlow()
    
    // Contacts count from repository
    val contactsCount: StateFlow<Int> = contactRepository.getAllContacts()
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    
    init {
        checkGpsStatus()
    }
    
    /**
     * Check GPS status.
     * Should be called periodically to update GPS status indicator.
     */
    fun checkGpsStatus() {
        _gpsEnabled.value = locationService.isLocationEnabled()
    }
    
    /**
     * Check permission status.
     */
    fun checkPermissions(): PermissionStatus {
        return PermissionStatus(
            hasLocationPermission = permissionManager.hasLocationPermission(),
            hasSmsPermission = permissionManager.hasSmsPermission()
        )
    }
    
    /**
     * Trigger emergency alert.
     * This is called when user confirms the emergency alert.
     */
    fun triggerEmergencyAlert() {
        // Prevent triggering if already in progress
        if (_emergencyState.value !is EmergencyState.Idle) {
            return
        }
        
        viewModelScope.launch {
            sendEmergencyAlert()
        }
    }
    
    /**
     * Send emergency alert with location and SMS.
     * Main business logic for emergency functionality.
     */
    private suspend fun sendEmergencyAlert() {
        try {
            // 1. Update state: Fetching location
            _emergencyState.value = EmergencyState.FetchingLocation
            
            // 2. Get GPS location (offline capable)
            var locationResult = locationService.getCurrentLocation()
            
            // 3. If current location fails, try last known location
            if (locationResult is LocationResult.Error) {
                locationResult = locationService.getLastKnownLocation()
                
                if (locationResult is LocationResult.Error) {
                    _emergencyState.value = EmergencyState.Error(
                        "Could not get location. Please try outdoors with GPS enabled."
                    )
                    return
                }
            }
            
            val location = locationResult as LocationResult.Success
            
            // 4. Get emergency contacts from database
            val contactList = contactRepository.getAllContacts().first()
            
            if (contactList.isEmpty()) {
                _emergencyState.value = EmergencyState.Error(
                    "No emergency contacts. Please add contacts first."
                )
                return
            }
            
            // 5. Update state: Sending SMS
            _emergencyState.value = EmergencyState.SendingSms
            
            // 6. Send SMS to all contacts (offline capable)
            val smsResult = smsService.sendEmergencyAlert(
                contacts = contactList,
                latitude = location.latitude,
                longitude = location.longitude
            )
            
            when (smsResult) {
                is SmsResult.Success -> {
                    if (smsResult.failedCount > 0) {
                        _emergencyState.value = EmergencyState.PartialSuccess(
                            sentCount = smsResult.sentCount,
                            failedCount = smsResult.failedCount,
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    } else {
                        _emergencyState.value = EmergencyState.Success(
                            sentCount = smsResult.sentCount,
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    }
                }
                is SmsResult.Error -> {
                    _emergencyState.value = EmergencyState.Error(smsResult.message)
                }
            }
            
        } catch (e: Exception) {
            _emergencyState.value = EmergencyState.Error(
                e.message ?: "Unknown error occurred"
            )
        }
    }
    
    /**
     * Reset emergency state to idle.
     */
    fun resetState() {
        _emergencyState.value = EmergencyState.Idle
    }
}

/**
 * Sealed class representing emergency alert states.
 */
sealed class EmergencyState {
    object Idle : EmergencyState()
    object FetchingLocation : EmergencyState()
    object SendingSms : EmergencyState()
    data class Success(
        val sentCount: Int,
        val latitude: Double,
        val longitude: Double
    ) : EmergencyState()
    data class PartialSuccess(
        val sentCount: Int,
        val failedCount: Int,
        val latitude: Double,
        val longitude: Double
    ) : EmergencyState()
    data class Error(val message: String) : EmergencyState()
}

/**
 * Data class for permission status.
 */
data class PermissionStatus(
    val hasLocationPermission: Boolean,
    val hasSmsPermission: Boolean
)
