# SahanaLanka Emergency Alert App

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-purple.svg)](https://developer.android.com/jetpack/compose)

A life-saving mobile application designed for Sri Lankan citizens to quickly access emergency services and send location-based alerts to emergency contacts during critical situations.

## 🚨 Key Features

### 1. Emergency Alert System (Offline-Capable)
Send your GPS location to emergency contacts via SMS with a single button press. **No internet required** - works with GPS satellites and cellular network only.

- 📍 **GPS Location Tracking** - High accuracy positioning with automatic fallback
- 💬 **Multi-Contact SMS** - Alert all emergency contacts simultaneously  
- 🔒 **Offline First** - Works without internet connection
- ⚡ **Fast Response** - Complete alert flow in under 15 seconds
- 🛡️ **Privacy Focused** - All data stored locally, no cloud tracking

[See detailed documentation →](EMERGENCY_ALERT_DOCUMENTATION.md)

### 2. Police Station Directory
Browse and call police stations across all 25 districts of Sri Lanka. Over 170 pre-loaded police stations with contact numbers.

- 🔍 **District Filtering** - Filter by any of Sri Lanka's 25 districts
- 📞 **One-Tap Calling** - Direct integration with phone dialer
- 🗂️ **Search Functionality** - Find stations by name, district, or number
- 💾 **Offline Access** - All data stored locally

### 3. Offline Emergency Guides
Step-by-step first aid instructions for 11+ critical emergency situations.

- 🫁 **CPR Instructions** - Adult and child CPR guides
- 🩹 **First Aid Guides** - Bleeding, burns, fractures, snake bites
- 🚑 **Medical Emergencies** - Heart attack, stroke, poisoning response
- ⚠️ **Safety Warnings** - Clear "DO NOT" warnings for each guide
- 📖 **Bookmark Guides** - Save frequently needed guides

### 4. Emergency Contacts Management
Maintain a list of up to 5 trusted emergency contacts who will receive alerts.

- 👥 **Contact Management** - Add, edit, delete emergency contacts
- ✅ **Quick Access** - Contacts available for instant alerting
- 🔐 **Local Storage** - Contacts stored securely on device

## 🎯 Target Audience

- Sri Lankan citizens and residents
- Travelers visiting Sri Lanka
- Elderly individuals living alone
- Parents wanting safety for their children
- People with medical conditions
- Anyone who values emergency preparedness

## 📱 Screenshots

[TODO: Add screenshots of Emergency Screen, Police Directory, Guides]

## 🛠️ Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt
- **Database**: Room (SQLite)
- **Location Services**: Google Play Services Location API
- **SMS**: Android SmsManager
- **Async Operations**: Kotlin Coroutines + Flow
- **Navigation**: Jetpack Navigation Compose

## 📋 Requirements

### Minimum Requirements
- Android 7.0 (API level 24) or higher
- GPS capability
- SMS capability
- ~20 MB storage space

### Permissions Required
- `ACCESS_FINE_LOCATION` - For GPS positioning
- `ACCESS_COARSE_LOCATION` - Fallback location
- `SEND_SMS` - For emergency alerts
- `CALL_PHONE` - For quick dialing police stations
- `VIBRATE` - For feedback on alert sent

All permissions are requested at runtime with clear explanations.

## 🚀 Getting Started

### Installation

#### Option 1: Download APK (Recommended for Users)
1. Download the latest APK from [Releases](../../releases)
2. Enable "Install from Unknown Sources" in device settings
3. Open the APK file and install
4. Grant required permissions when prompted

#### Option 2: Build from Source (For Developers)

```bash
# Clone the repository
git clone https://github.com/HasiruChamodya/mobile-app-v2.git
cd mobile-app-v2

# Open in Android Studio
# Sync Gradle dependencies
# Run on emulator or physical device
```

### First-Time Setup

1. **Grant Permissions**: Allow location and SMS permissions
2. **Add Emergency Contacts**: Tap "Manage Contacts" and add 1-5 trusted contacts
3. **Enable GPS**: Ensure GPS is enabled in device settings
4. **Test Alert** (Optional): Send a test alert to verify setup

## 📖 Usage Guide

### Sending an Emergency Alert

1. Open the app to the Emergency screen
2. Verify:
   - ✅ GPS status shows "GPS Active"
   - ✅ Contacts count shows at least 1 contact
3. **Press and hold** the red emergency button for 3 seconds
4. Confirm by tapping "Send Alert"
5. Wait while app:
   - Fetches your GPS location (~10 seconds)
   - Sends SMS to all contacts (~5 seconds)
6. Success message confirms alert sent

### Adding Emergency Contacts

1. Tap "Manage Contacts" button on Emergency screen
2. Tap the "+" button
3. Enter contact name and phone number
4. Tap "Save"

Repeat for up to 5 contacts.

### Browsing Police Directory

1. Tap "Police" icon in bottom navigation
2. Browse all police stations (default view)
3. Filter by district using the chips at top
4. Search using the search icon
5. Tap call button on any card to dial

### Accessing Emergency Guides

1. Tap "Guides" icon in bottom navigation
2. Browse all available guides
3. Filter by category: Breathing, Injuries, Medical, Environmental
4. Tap any guide to view detailed steps
5. Bookmark guides for quick access

## 🔒 Privacy & Security

### Data Storage
- ✅ All data stored **locally on device**
- ✅ No cloud storage or external servers
- ✅ No user tracking or analytics
- ✅ Contacts never leave your device (except via SMS during alert)

### Location Privacy
- ✅ Location accessed **only when emergency alert triggered**
- ✅ No background location tracking
- ✅ Location not stored after alert sent
- ✅ Complete user control

### Permissions
- ✅ Runtime permissions with clear explanations
- ✅ Graceful degradation if permissions denied
- ✅ Can revoke permissions anytime in Settings

## ❓ Troubleshooting

### Emergency button is disabled
**Causes:**
- GPS is disabled → Enable GPS in device settings
- No contacts added → Add at least 1 emergency contact
- Permissions denied → Grant location and SMS permissions

### "Could not get location" error
**Solutions:**
1. Enable GPS in device settings
2. Go outdoors for better satellite visibility
3. Wait 30 seconds for GPS to acquire signal
4. Try again

### SMS not received by contacts
**Causes:**
- No cellular signal → Move to area with signal
- Invalid phone number → Verify contact number in contacts list
- Carrier SMS limit → Check with your mobile carrier

For more troubleshooting, see [Emergency Alert Documentation](EMERGENCY_ALERT_DOCUMENTATION.md).

## 🏗️ Project Structure

```
app/src/main/java/com/shanalanka/emergency/
├── core/
│   └── di/                        # Dependency injection modules
├── data/
│   ├── local/                     # Room database (DAOs, entities)
│   ├── model/                     # Data models
│   ├── models/                    # UI models
│   ├── repository/                # Repository layer
│   └── source/                    # Pre-populated data
├── domain/
│   └── service/                   # Business logic (Location, SMS, Permissions)
├── ui/
│   ├── components/                # Reusable UI components
│   ├── navigation/                # Navigation graph
│   ├── screens/                   # Screen composables
│   ├── theme/                     # Material 3 theme
│   └── viewmodel/                 # ViewModels for state management
└── util/                          # Utility classes
```

## 📚 Documentation

- [Emergency Alert System](EMERGENCY_ALERT_DOCUMENTATION.md) - Detailed emergency functionality guide
- [Features Documentation](FEATURES_DOCUMENTATION.md) - Police directory & emergency guides
- [Project Structure](PROJECT_STRUCTURE.md) - Architecture overview
- [UI Implementation](UI_IMPLEMENTATION.md) - UI components guide
- [Implementation Summary](IMPLEMENTATION_SUMMARY.md) - Development summary

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Follow existing code style (Kotlin conventions)
4. Write meaningful commit messages
5. Test your changes thoroughly
6. Submit a pull request

### Areas for Contribution
- 🌐 Translation to Sinhala and Tamil
- 🎨 UI/UX improvements
- 📝 Additional emergency guides
- 🗺️ More police stations data
- 🧪 Unit and integration tests
- 📖 Documentation improvements

## 🐛 Bug Reports

Found a bug? Please open an issue with:
- Clear description of the issue
- Steps to reproduce
- Expected vs actual behavior
- Screenshots if applicable
- Device model and Android version

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Emergency guides content based on international first aid standards
- Police station data compiled from public sources
- Icons from Material Icons library
- Built with Android Jetpack libraries

## 📞 Contact & Support

- **Repository**: [HasiruChamodya/mobile-app-v2](https://github.com/HasiruChamodya/mobile-app-v2)
- **Issues**: [GitHub Issues](../../issues)
- **Discussions**: [GitHub Discussions](../../discussions)

## ⚠️ Disclaimer

This app is designed to assist in emergency situations but should not be relied upon as the sole means of emergency communication. Always call official emergency services (119 in Sri Lanka) when possible. The developers are not responsible for any outcomes resulting from the use or misuse of this application.

## 🌟 Star This Project

If you find SahanaLanka helpful, please consider giving it a star ⭐ on GitHub. It helps others discover the project!

---

**Made with ❤️ for Sri Lanka**
