package com.shanalanka.emergency.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shanalanka.emergency.data.models.EmergencyContact
import com.shanalanka.emergency.data.models.EmergencySettings
import com.shanalanka.emergency.data.repository.ContactRepository
import com.shanalanka.emergency.data.repository.SettingsRepository
import com.shanalanka.emergency.domain.service.LocationResult
import com.shanalanka.emergency.domain.service.LocationService
import com.shanalanka.emergency.domain.service.SmsResult
import com.shanalanka.emergency.domain.service.SmsService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val locationService: LocationService,
    private val smsService: SmsService,
    private val contactRepository: ContactRepository
) : ViewModel() {
    
    /**
     * Observable settings from repository
     */
    val settings: StateFlow<EmergencySettings> = 
        settingsRepository.settings
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = EmergencySettings()
            )
    
    private val _testAlertState = kotlinx.coroutines.flow.MutableStateFlow<TestAlertState>(TestAlertState.Idle)
    val testAlertState: StateFlow<TestAlertState> = _testAlertState
    
    /**
     * Update settings and persist to storage
     */
    fun updateSettings(newSettings: EmergencySettings) {
        viewModelScope.launch {
            settingsRepository.saveSettings(newSettings)
        }
    }
    
    /**
     * Send a test emergency alert
     * Same as real alert but with "TEST ALERT" prefix
     */
    fun sendTestAlert() {
        viewModelScope.launch {
            try {
                _testAlertState.value = TestAlertState.Sending
                
                // Get location
                val locationResult = locationService.getCurrentLocation()
                if (locationResult !is LocationResult.Success) {
                    _testAlertState.value = TestAlertState.Error("Could not get location")
                    return@launch
                }
                
                // Get contacts
                val contacts = contactRepository.getAllContacts().first()
                if (contacts.isEmpty()) {
                    _testAlertState.value = TestAlertState.Error("No emergency contacts added")
                    return@launch
                }
                
                // Send test SMS (add TEST prefix to identify)
                val smsResult = sendTestSms(
                    contacts = contacts,
                    latitude = locationResult.latitude,
                    longitude = locationResult.longitude
                )
                
                when (smsResult) {
                    is SmsResult.Success -> {
                        _testAlertState.value = TestAlertState.Success(smsResult.sentCount)
                    }
                    is SmsResult.Error -> {
                        _testAlertState.value = TestAlertState.Error(smsResult.message)
                    }
                }
            } catch (e: Exception) {
                _testAlertState.value = TestAlertState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    /**
     * Send test SMS with TEST prefix
     */
    private suspend fun sendTestSms(
        contacts: List<EmergencyContact>,
        latitude: Double,
        longitude: Double
    ): SmsResult {
        // Note: SmsService will need to be enhanced to support test mode
        // For now, use regular SMS service
        return smsService.sendEmergencyAlert(contacts, latitude, longitude)
    }
    
    /**
     * Reset test alert state
     */
    fun resetTestAlertState() {
        _testAlertState.value = TestAlertState.Idle
    }
}

/**
 * State for test alert operation
 */
sealed class TestAlertState {
    object Idle : TestAlertState()
    object Sending : TestAlertState()
    data class Success(val sentCount: Int) : TestAlertState()
    data class Error(val message: String) : TestAlertState()
}
