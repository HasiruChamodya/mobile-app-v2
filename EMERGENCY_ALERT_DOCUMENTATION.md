# Emergency Alert Functionality Documentation

## Overview

The Emergency Alert functionality is the core feature of the SahanaLanka app, enabling users to send their GPS location to emergency contacts via SMS during critical situations. This feature is designed to work **offline** using only GPS satellites and cellular network - no internet connection required.

## Key Features

### 1. GPS Location Tracking (Offline-Capable)
- **Accurate GPS positioning** using Google Play Services Location API
- **High accuracy mode** with 10-second timeout for emergency situations
- **Automatic fallback** to last known location if current location unavailable
- **Real-time GPS status indicator** updates every 5 seconds (lifecycle-aware)
- **Works completely offline** - uses GPS satellites, no internet needed

### 2. SMS Emergency Alerts (Offline-Capable)
- **Multi-contact support** - sends SMS to all emergency contacts simultaneously
- **Smart message format** - includes both raw coordinates (for offline use) and Google Maps link (for online convenience)
- **Multi-part SMS support** - handles long messages automatically
- **Partial success handling** - tracks which contacts received alerts
- **Cellular network only** - no internet connection required

### 3. Permission Management
- **Runtime permission requests** for location and SMS
- **Permission rationale dialogs** explaining why permissions are needed
- **Graceful handling** of denied permissions
- **Settings deep-link** for manually granting permissions

### 4. User Experience
- **Loading indicators** during GPS fetch and SMS sending
- **Clear status feedback** with success/error dialogs
- **Progress tracking** - "Getting your location..." → "Sending emergency alerts..."
- **Partial success reporting** - "Alert sent to 2 of 3 contacts"
- **Emergency button safeguards** - requires press and hold + confirmation dialog

## Implementation Details

### Architecture

The emergency alert system follows clean architecture principles with three layers:

```
├── domain/service/           # Business logic layer
│   ├── LocationService.kt    # Interface for GPS operations
│   ├── LocationServiceImpl.kt
│   ├── SmsService.kt         # Interface for SMS operations
│   ├── SmsServiceImpl.kt
│   └── PermissionManager.kt  # Permission utilities
│
├── ui/viewmodel/             # Presentation layer
│   └── EmergencyViewModel.kt # State management & alert flow
│
├── ui/screens/               # UI layer
│   └── EmergencyScreen.kt    # Main emergency screen
│
├── ui/components/            # Reusable UI components
│   └── PermissionRequestDialog.kt
│
├── data/repository/          # Data layer
│   └── ContactRepository.kt  # Emergency contacts access
│
└── util/                     # Utilities
    └── LocationFormatter.kt  # Coordinate formatting
```

### Emergency Alert Flow

```kotlin
User presses emergency button (hold 3 seconds)
    ↓
Confirmation dialog shown
    ↓
User confirms → EmergencyViewModel.triggerEmergencyAlert()
    ↓
State: FetchingLocation
    ↓
LocationService.getCurrentLocation() [10s timeout]
    ↓ (if fails)
LocationService.getLastKnownLocation()
    ↓
State: SendingSms
    ↓
SmsService.sendEmergencyAlert(contacts, lat, lon)
    ↓
State: Success | PartialSuccess | Error
    ↓
Feedback dialog shown to user
```

### SMS Message Format

The emergency SMS is optimized for both offline and online scenarios:

```
🆘 EMERGENCY! I need help.

📍 Location:
Lat: 6.927079
Lon: 79.861244

🗺️ Map Link:
https://maps.google.com/?q=6.927079,79.861244

Sent from SahanaLanka App
```

**Why this format:**
- ✅ Clear emergency indicator (🆘)
- ✅ Raw coordinates work offline (can be read to emergency services over phone)
- ✅ Google Maps link for convenience when internet available
- ✅ Fits in standard SMS length
- ✅ Easy for Sri Lankan emergency services (119, ambulance) to use

### Location Accuracy

- Coordinates formatted to **6 decimal places**
- Provides **~0.1 meter accuracy**
- Sufficient for emergency responders to locate the person

## Usage Guide

### For Users

#### Prerequisites
1. **Location permission** granted (requested on first launch)
2. **SMS permission** granted (requested on first launch)
3. **GPS enabled** in device settings
4. **At least one emergency contact** added to the app

#### Sending an Emergency Alert

1. Open the SahanaLanka app
2. Verify GPS status indicator shows "GPS Active"
3. Verify contacts count shows "X Emergency Contacts"
4. **Press and hold** the large red emergency button for 3 seconds
5. Confirm in the dialog by tapping "Send Alert"
6. Wait while the app:
   - Fetches your GPS location (~10 seconds max)
   - Sends SMS to all emergency contacts (~5 seconds)
7. Success dialog shows:
   - Number of contacts alerted
   - Your exact GPS coordinates

### For Developers

#### Adding Dependencies

```kotlin
// app/build.gradle.kts
dependencies {
    // Location Services
    implementation("com.google.android.gms:play-services-location:21.1.0")
}
```

#### Required Permissions

```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.SEND_SMS" />
```

#### Using the Services

```kotlin
// Inject services via Hilt
@HiltViewModel
class EmergencyViewModel @Inject constructor(
    private val locationService: LocationService,
    private val smsService: SmsService,
    private val contactRepository: ContactRepository
) : ViewModel()

// Get location
suspend fun getLocation() {
    val result = locationService.getCurrentLocation()
    when (result) {
        is LocationResult.Success -> {
            // Use result.latitude and result.longitude
        }
        is LocationResult.Error -> {
            // Handle error
        }
    }
}

// Send SMS
suspend fun sendAlert(lat: Double, lon: Double) {
    val contacts = contactRepository.getAllContacts().first()
    val result = smsService.sendEmergencyAlert(contacts, lat, lon)
    when (result) {
        is SmsResult.Success -> {
            // result.sentCount, result.failedCount
        }
        is SmsResult.Error -> {
            // Handle error
        }
    }
}
```

## State Management

The `EmergencyViewModel` manages six states:

```kotlin
sealed class EmergencyState {
    object Idle                                    // Ready to send alert
    object FetchingLocation                        // Getting GPS location
    object SendingSms                              // Sending SMS messages
    data class Success(...)                        // All SMS sent successfully
    data class PartialSuccess(...)                 // Some SMS sent successfully
    data class Error(message: String)              // Failed to send alert
}
```

## Error Handling

### Scenario: No GPS Signal
- **Behavior**: Tries for 10 seconds, then falls back to last known location
- **Message**: "Could not get location. Please try outdoors with GPS enabled."

### Scenario: GPS Disabled
- **Behavior**: Emergency button disabled, GPS indicator shows red
- **Message**: "Enable GPS to activate"

### Scenario: No Emergency Contacts
- **Behavior**: Emergency button disabled
- **Message**: "Add emergency contacts to enable"

### Scenario: Permission Denied
- **Behavior**: Rationale dialog shown, then permission request
- **If permanently denied**: Dialog with "Open Settings" button

### Scenario: SMS Send Failure
- **Behavior**: Tracks successful/failed sends per contact
- **Message**: "Alert sent to 2 of 3 contacts. 1 message failed to send."

### Scenario: Concurrent Alert Triggers
- **Behavior**: Subsequent triggers ignored while alert in progress
- **Protection**: State check in `triggerEmergencyAlert()`

## Performance Characteristics

### Timing
- **GPS acquisition**: ~1-10 seconds (depends on signal)
- **SMS sending**: ~1-5 seconds
- **Total flow**: ~2-15 seconds

### Battery Impact
- **GPS checking**: Every 5 seconds when screen active
- **Optimization**: Lifecycle-aware (stops when screen inactive)
- **Location request**: One-time, high accuracy

### Offline Capability
- ✅ **GPS**: Works offline (satellite-based)
- ✅ **SMS**: Works offline (cellular network only)
- ❌ **Internet**: Not required for any functionality

## Security & Privacy

### Data Storage
- **Emergency contacts**: Stored locally in Room database
- **Location history**: Not stored (only fetched on demand)
- **No cloud storage**: All data remains on device

### Data Transmission
- **SMS only**: Direct to user's contacts (no intermediary)
- **No tracking**: App doesn't track location in background
- **Explicit user action**: Location sent only when user triggers alert

### Permissions
- **Runtime permissions**: Requested only when needed
- **Permission rationale**: Clear explanation before request
- **Graceful degradation**: App functional without permissions (with limited features)

## Testing

### Manual Testing Checklist

- [ ] Emergency button disabled when GPS off
- [ ] Emergency button disabled when no contacts
- [ ] Confirmation dialog shows before sending
- [ ] Loading overlay displays during GPS fetch
- [ ] Loading overlay displays during SMS send
- [ ] Success dialog shows correct sent count
- [ ] Success dialog shows GPS coordinates
- [ ] Partial success shows sent/failed counts
- [ ] Error dialog shows clear error message
- [ ] GPS status indicator updates in real-time
- [ ] Permission dialogs display correctly
- [ ] Settings deep-link works from denied dialog
- [ ] SMS contains correct coordinates format
- [ ] SMS contains Google Maps link
- [ ] Multiple contacts receive SMS
- [ ] Works without internet connection
- [ ] Works with GPS but no mobile data
- [ ] Falls back to last known location

### Simulating Scenarios

#### Test with No GPS Signal
```kotlin
// Disable GPS in device settings
// Try to send alert
// Should show error: "Could not get location..."
```

#### Test with No Contacts
```kotlin
// Delete all emergency contacts
// Emergency button should be disabled
// Should show: "Add emergency contacts to enable"
```

#### Test SMS Sending
```kotlin
// Add your own number as contact
// Send test alert
// Verify SMS received with correct format
```

## Troubleshooting

### Issue: "Could not get location"
**Solution**: 
1. Enable GPS in device settings
2. Go outdoors for better satellite visibility
3. Wait ~30 seconds for GPS to acquire satellites
4. Try again

### Issue: "Permission denied"
**Solution**:
1. Tap "Open Settings" in the denied dialog
2. Find SahanaLanka app
3. Enable Location and SMS permissions
4. Return to app and try again

### Issue: Emergency button disabled
**Possible causes**:
- GPS is disabled → Enable GPS
- No contacts added → Add emergency contacts via "Manage Contacts"
- Permissions denied → Grant permissions via Settings

### Issue: SMS not received
**Possible causes**:
- No cellular signal → Move to area with signal
- Invalid phone number → Verify contact number
- SMS quota exceeded → Check with carrier

## Future Enhancements

### Planned Features
- [ ] Test alert button (non-emergency test)
- [ ] Location history (opt-in)
- [ ] Custom SMS message templates
- [ ] Multiple language support
- [ ] Vibration/sound feedback on success
- [ ] Alert cooldown period (prevent spam)
- [ ] Battery level in SMS
- [ ] Automatic retry for failed SMS

### Considerations
- Background location tracking (with explicit consent)
- Automatic alert on device shake/fall detection
- Integration with wearable devices
- Voice alert via phone call
- Photo attachment (requires internet)

## Technical Notes

### Why Google Play Services Location?
- Most accurate location provider
- Better battery efficiency than raw GPS
- Handles provider selection automatically
- Industry standard for Android

### Why SMS over Internet?
- SMS works without data/WiFi
- More reliable in emergencies
- Works in remote areas
- No dependency on app server
- Recipient doesn't need app installed

### Why 10 Second Timeout?
- Balance between accuracy and urgency
- Typical GPS acquisition: 1-10 seconds
- Prevents indefinite waiting
- Falls back to last known location

### Thread Safety
- All operations use `viewModelScope`
- Coroutines with proper cancellation
- StateFlow for thread-safe state management
- No shared mutable state

## Credits

**Implemented by**: GitHub Copilot (AI Pair Programmer)
**Repository**: HasiruChamodya/mobile-app-v2
**Date**: January 2026
**Language**: Kotlin
**Framework**: Jetpack Compose
**Architecture**: MVVM + Clean Architecture
