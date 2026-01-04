# Police Directory & Emergency Guides Features

## Overview

This document describes two major features added to the SahanaLanka Emergency Alert application:

1. **Police Station Directory** - Pre-loaded database of police station phone numbers for all 25 districts in Sri Lanka
2. **Offline Emergency Guides** - Comprehensive emergency situation guides accessible offline

## Feature 1: Police Station Directory

### Description

Users can browse and quickly call police stations across all 25 districts of Sri Lanka. The app includes over 170 pre-loaded police stations with their contact numbers.

### Key Features

- **District Filtering**: Filter police stations by any of Sri Lanka's 25 districts
- **Search Functionality**: Search police stations by name, district, or phone number
- **One-Tap Calling**: Direct integration with the device's phone dialer
- **Offline Access**: All data stored locally in Room database

### Implementation Details

#### Data Layer

- **Models**: `District.kt`, `PoliceStation.kt`
- **Database**: `PoliceStationEntity.kt`, `PoliceStationDao.kt`
- **Repository**: `PoliceStationRepository.kt`
- **Data Source**: `PoliceStationData.kt` (170+ police stations pre-populated)

#### UI Layer

- **Screen**: `PoliceDirectoryScreen.kt`
- **Components**: `PoliceStationCard.kt`, `DistrictFilterChip.kt`
- **ViewModel**: `PoliceDirectoryViewModel.kt`

#### Navigation

- Accessible via bottom navigation bar (Police icon)
- Route: `"police"`

### Usage

1. Tap on the "Police" icon in the bottom navigation
2. Browse police stations (default shows all districts)
3. Filter by specific district using the horizontal filter chips
4. Search for specific stations using the search icon
5. Tap the call button to dial the police station directly

### Database Pre-population

Police station data is automatically populated on first app launch via the Room database callback in `DatabaseModule.kt`.

## Feature 2: Offline Emergency Guides

### Description

Comprehensive, offline-accessible emergency guides that provide step-by-step instructions for critical emergency situations.

### Available Guides

1. **CPR - Adult** (8 steps)
2. **CPR - Child** (9 steps)
3. **Choking - Heimlich Maneuver** (7 steps)
4. **Snake Bite First Aid** (9 steps)
5. **Severe Bleeding Control** (8 steps)
6. **Burn Treatment** (8 steps)
7. **Fracture First Aid** (8 steps)
8. **Heart Attack Response** (7 steps)
9. **Stroke - FAST Method** (8 steps)
10. **Drowning Rescue** (8 steps)
11. **Poisoning Response** (8 steps)

### Key Features

- **Step-by-Step Instructions**: Clear, numbered steps for each emergency
- **Warnings Section**: Important "DO NOT" warnings highlighted
- **When to Call Emergency**: Prominent section explaining when to call 119
- **Bookmark Functionality**: Save favorite guides for quick access
- **Category Filtering**: Filter guides by category (Breathing, Injuries, Medical, Environmental)
- **Search Functionality**: Search guides by title, description, or step content
- **Offline Access**: All guide data stored locally in Room database

### Implementation Details

#### Data Layer

- **Models**: `EmergencyGuide.kt`, `GuideStep.kt`, `GuideCategory.kt`
- **Database**: `EmergencyGuideEntity.kt`, `GuideStepEntity.kt`, `GuideWarningEntity.kt`
- **DAO**: `EmergencyGuideDao.kt`
- **Repository**: `EmergencyGuideRepository.kt`
- **Data Source**: `EmergencyGuidesData.kt` (11 comprehensive guides pre-populated)

#### UI Layer

- **Screens**: 
  - `EmergencyGuidesScreen.kt` (List view with bookmarked section)
  - `GuideDetailScreen.kt` (Full guide with steps and warnings)
- **Components**: 
  - `GuideCard.kt` (Guide preview card)
  - `GuideStepCard.kt` (Individual step display)
  - `CategoryTab.kt` (Category filter tabs)
- **ViewModel**: `EmergencyGuidesViewModel.kt`

#### Navigation

- Accessible via bottom navigation bar (Guides icon)
- Routes: 
  - List: `"guides"`
  - Detail: `"guide_detail/{guideId}"`

### Usage

1. Tap on the "Guides" icon in the bottom navigation
2. Browse all available guides
3. Tap the bookmark icon to save favorites
4. Filter by category using the horizontal filter tabs
5. Search for specific guides using the search icon
6. Tap on any guide to view full details
7. View step-by-step instructions, warnings, and emergency call information

### Guide Structure

Each guide includes:
- **Title**: Clear, concise name
- **Category**: One of five categories (Breathing, Injuries, Medical, Environmental, Other)
- **Description**: Brief overview of the emergency
- **Steps**: Numbered, sequential instructions
- **Warnings**: Critical "DO NOT" items
- **When to Call Emergency**: Clear guidance on when to call 119

### Color-Coded Categories

- **Breathing** (Blue #2196F3): CPR, Choking
- **Injuries** (Red #D32F2F): Bleeding, Burns, Fractures
- **Medical** (Purple #7B1FA2): Heart Attack, Stroke, Poisoning
- **Environmental** (Green #388E3C): Snake Bites, Drowning
- **Other** (Gray #616161): Miscellaneous emergencies

## Navigation Updates

### Bottom Navigation Bar

The app now includes a 5-tab bottom navigation bar:

1. **Emergency** (Warning icon) - Main emergency alert screen
2. **Police** (Police badge icon) - Police station directory
3. **Guides** (Book icon) - Emergency guides
4. **Contacts** (Contacts icon) - Emergency contacts
5. **Settings** (Settings icon) - App settings

The bottom navigation is implemented in `MainActivity.kt` and only displays on main screens (not on detail screens like `GuideDetailScreen`).

## Database Architecture

### Schema

The app uses Room database with the following tables:

#### police_stations
- `id` (INTEGER PRIMARY KEY)
- `name` (TEXT)
- `district` (TEXT)
- `phone_number` (TEXT)
- `address` (TEXT, nullable)

#### emergency_guides
- `id` (TEXT PRIMARY KEY)
- `title` (TEXT)
- `category` (TEXT)
- `description` (TEXT)
- `when_to_call_emergency` (TEXT)
- `icon_res` (INTEGER)
- `is_bookmarked` (INTEGER)

#### guide_steps
- `id` (INTEGER PRIMARY KEY)
- `guide_id` (TEXT FOREIGN KEY)
- `step_number` (INTEGER)
- `title` (TEXT)
- `description` (TEXT)
- `image_res` (INTEGER, nullable)

#### guide_warnings
- `id` (INTEGER PRIMARY KEY)
- `guide_id` (TEXT FOREIGN KEY)
- `warning_text` (TEXT)

### Database Version

Updated from version 1 to version 2 with the new entities.

### Pre-population

Data is automatically pre-populated on first app launch via Room database callback in `DatabaseModule.kt`:
- 170+ police stations
- 11 emergency guides
- 88 guide steps
- 35+ warnings

## Permissions

### Added Permissions

- `android.permission.CALL_PHONE` - Required for direct phone dialing from police directory

### Permission Handling

The app uses `ACTION_DIAL` intent which does not require runtime permission - it opens the phone dialer with the number pre-filled, allowing the user to confirm before calling.

## Dependency Injection

Both features are fully integrated with Hilt dependency injection:

- `PoliceStationRepository` - Singleton providing police station data
- `EmergencyGuideRepository` - Singleton providing emergency guide data
- `PoliceDirectoryViewModel` - Scoped ViewModel for police directory
- `EmergencyGuidesViewModel` - Scoped ViewModel for emergency guides

All dependencies are provided via `DatabaseModule.kt`.

## Offline-First Architecture

Both features are designed with an offline-first approach:

1. **No Network Required**: All data stored locally in Room database
2. **Instant Access**: Data loads from local database with no latency
3. **Reliable**: Works in any situation, including areas with no connectivity
4. **Efficient**: Uses Flow and StateFlow for reactive data updates

## Testing

### Preview Functions

All composables include `@Preview` functions for visual testing:
- `PoliceStationCardPreview`
- `DistrictFilterChipPreview`
- `GuideCardPreview`
- `GuideStepCardPreview`
- `EmergencyGuidesScreenPreview`
- etc.

### Manual Testing Checklist

- [ ] Police stations load on first app launch
- [ ] District filtering works correctly
- [ ] Police station search functions properly
- [ ] Phone call intent works (opens dialer with number)
- [ ] Emergency guides load on first app launch
- [ ] Guide bookmarking persists across app sessions
- [ ] Category filtering works correctly
- [ ] Guide search functions properly
- [ ] Guide detail screen shows all information
- [ ] Bottom navigation works on all screens
- [ ] App works in airplane mode (offline)

## Future Enhancements

### Police Directory
- Add GPS-based sorting (nearest stations first)
- Include station operating hours
- Add station photos and facility information
- Support for Sinhala and Tamil languages

### Emergency Guides
- Add illustrations/diagrams for steps
- Video demonstrations (downloaded for offline use)
- Audio narration for hands-free access
- Sinhala and Tamil translations
- Quiz mode for learning first aid
- Share functionality for guide content

## Technical Notes

### Important Files Created/Modified

**Data Layer:**
- `app/src/main/java/com/shanalanka/emergency/data/model/District.kt`
- `app/src/main/java/com/shanalanka/emergency/data/model/PoliceStation.kt`
- `app/src/main/java/com/shanalanka/emergency/data/model/EmergencyGuide.kt`
- `app/src/main/java/com/shanalanka/emergency/data/model/GuideStep.kt`
- `app/src/main/java/com/shanalanka/emergency/data/model/GuideCategory.kt`
- `app/src/main/java/com/shanalanka/emergency/data/local/entity/PoliceStationEntity.kt`
- `app/src/main/java/com/shanalanka/emergency/data/local/entity/EmergencyGuideEntity.kt`
- `app/src/main/java/com/shanalanka/emergency/data/local/dao/PoliceStationDao.kt`
- `app/src/main/java/com/shanalanka/emergency/data/local/dao/EmergencyGuideDao.kt`
- `app/src/main/java/com/shanalanka/emergency/data/repository/PoliceStationRepository.kt`
- `app/src/main/java/com/shanalanka/emergency/data/repository/EmergencyGuideRepository.kt`
- `app/src/main/java/com/shanalanka/emergency/data/source/PoliceStationData.kt`
- `app/src/main/java/com/shanalanka/emergency/data/source/EmergencyGuidesData.kt`
- `app/src/main/java/com/shanalanka/emergency/data/local/AppDatabase.kt` (updated)
- `app/src/main/java/com/shanalanka/emergency/core/di/DatabaseModule.kt` (updated)

**UI Layer:**
- `app/src/main/java/com/shanalanka/emergency/ui/screens/PoliceDirectoryScreen.kt`
- `app/src/main/java/com/shanalanka/emergency/ui/screens/EmergencyGuidesScreen.kt`
- `app/src/main/java/com/shanalanka/emergency/ui/screens/GuideDetailScreen.kt`
- `app/src/main/java/com/shanalanka/emergency/ui/components/PoliceStationCard.kt`
- `app/src/main/java/com/shanalanka/emergency/ui/components/GuideCard.kt`
- `app/src/main/java/com/shanalanka/emergency/ui/components/GuideStepCard.kt`
- `app/src/main/java/com/shanalanka/emergency/ui/components/DistrictFilterChip.kt`
- `app/src/main/java/com/shanalanka/emergency/ui/components/CategoryTab.kt`
- `app/src/main/java/com/shanalanka/emergency/ui/viewmodel/PoliceDirectoryViewModel.kt`
- `app/src/main/java/com/shanalanka/emergency/ui/viewmodel/EmergencyGuidesViewModel.kt`

**Navigation:**
- `app/src/main/java/com/shanalanka/emergency/ui/navigation/Screen.kt` (updated)
- `app/src/main/java/com/shanalanka/emergency/ui/navigation/NavGraph.kt` (updated)
- `app/src/main/java/com/shanalanka/emergency/MainActivity.kt` (updated with bottom nav)

**Manifest:**
- `app/src/main/AndroidManifest.xml` (added CALL_PHONE permission)

### Code Statistics
- **Total Files Created**: 29
- **Total Files Modified**: 6
- **Lines of Code Added**: ~3,500+
- **Police Stations Pre-loaded**: 170+
- **Emergency Guides**: 11
- **Guide Steps**: 88
- **Guide Warnings**: 35+

## Support

For questions or issues related to these features, please refer to:
- App codebase documentation
- Room database documentation: https://developer.android.com/training/data-storage/room
- Jetpack Compose documentation: https://developer.android.com/jetpack/compose
- Material 3 documentation: https://m3.material.io/

## Disclaimer

**Medical Information Disclaimer**: The emergency guides provided in this app are for informational purposes only and should not replace professional medical advice, diagnosis, or treatment. Always seek the advice of qualified medical professionals for any emergency situation. In case of emergency, call 119 or visit the nearest hospital immediately.

**Police Contact Information**: Police station phone numbers should be verified and updated regularly. The numbers provided are sample/placeholder numbers and should be updated with official police hotline numbers from the Sri Lanka Police Department.
