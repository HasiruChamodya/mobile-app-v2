# SahanaLanka Emergency Alert UI - Visual Guide

## Screen Mockups (ASCII Art)

### 1. Emergency Screen (Main Screen)
```
┌──────────────────────────────────────┐
│ SahanaLanka Emergency        ⚙      │  ← Top App Bar (Red)
├──────────────────────────────────────┤
│                                      │
│        ┌──────────────────┐          │
│        │ 📍 GPS Active    │          │  ← Status Chips
│        └──────────────────┘          │
│                                      │
│        ┌──────────────────┐          │
│        │ 👥 3 Emergency   │          │
│        │    Contacts      │          │
│        └──────────────────┘          │
│                                      │
│                                      │
│   Press and hold for 3 seconds      │  ← Instructions
│   to send emergency alert            │
│                                      │
│            ┌──────┐                  │
│            │      │                  │
│            │ SOS  │                  │  ← Emergency Button
│            │      │                  │     (200dp, Red)
│            └──────┘                  │
│                                      │
│                                      │
│                                      │
│  ┌──────────────────────────────┐   │
│  │ 👥 Manage Contacts           │   │  ← Quick Action
│  └──────────────────────────────┘   │
│                                      │
└──────────────────────────────────────┘
```

### 2. Contacts Screen
```
┌──────────────────────────────────────┐
│ ← Emergency Contacts                 │  ← Top App Bar (Red)
├──────────────────────────────────────┤
│                                      │
│   3/5 contacts added                 │  ← Contact Limit
│                                      │
├──────────────────────────────────────┤
│  ┌────────────────────────────────┐ │
│  │ 👤  John Doe                   │ │  ← Contact Cards
│  │     +94 77 123 4567         🗑 │ │
│  └────────────────────────────────┘ │
│                                      │
│  ┌────────────────────────────────┐ │
│  │ 👤  Jane Smith                 │ │
│  │     +94 71 987 6543         🗑 │ │
│  └────────────────────────────────┘ │
│                                      │
│  ┌────────────────────────────────┐ │
│  │ 👤  Bob Johnson                │ │
│  │     +94 76 555 1234         🗑 │ │
│  └────────────────────────────────┘ │
│                                      │
│                                      │
│                              ┌────┐  │
│                              │ +  │  │  ← FAB (Add Contact)
│                              └────┘  │
└──────────────────────────────────────┘
```

### 3. Contacts Screen (Empty State)
```
┌──────────────────────────────────────┐
│ ← Emergency Contacts                 │  ← Top App Bar (Red)
├──────────────────────────────────────┤
│                                      │
│                                      │
│             👤➕                      │
│            /  \                      │
│           /    \                     │  ← Empty State Icon
│                                      │
│                                      │
│     No Emergency Contacts            │
│                                      │
│   Add emergency contacts to send     │
│   alerts in critical situations      │
│                                      │
│  ┌──────────────────────────────┐   │
│  │  + Add First Contact         │   │  ← Action Button
│  └──────────────────────────────┘   │
│                                      │
│                                      │
│                              ┌────┐  │
│                              │ +  │  │  ← FAB
│                              └────┘  │
└──────────────────────────────────────┘
```

### 4. Settings Screen
```
┌──────────────────────────────────────┐
│ ← Settings                           │  ← Top App Bar (Red)
├──────────────────────────────────────┤
│                                      │
│ Emergency Message                    │
│ ┌──────────────────────────────────┐ │
│ │ I'm in an emergency. My          │ │  ← Text Field
│ │ location is: [GPS_LINK]          │ │
│ │                                  │ │
│ └──────────────────────────────────┘ │
│ Use [GPS_LINK] for location          │
│                                      │
│ ──────────────────────────────────── │
│                                      │
│ GPS Settings                         │
│                                      │
│ High Accuracy GPS            [ON]    │  ← Switches
│ More precise location, uses more...  │
│                                      │
│ ──────────────────────────────────── │
│                                      │
│ Feedback                             │
│                                      │
│ Sound                        [ON]    │
│ Play sound when alert is sent        │
│                                      │
│ Vibration                    [ON]    │
│ Vibrate when alert is sent           │
│                                      │
│ ──────────────────────────────────── │
│                                      │
│ Testing                              │
│ ┌──────────────────────────────────┐ │
│ │    Send Test Alert               │ │  ← Test Button
│ └──────────────────────────────────┘ │
│                                      │
│ ──────────────────────────────────── │
│                                      │
│ ┌──────────────────────────────────┐ │
│ │ ℹ️  SahanaLanka Emergency        │ │  ← App Info
│ │    Version 1.0                   │ │
│ │    Emergency alert system for    │ │
│ │    Sri Lanka                     │ │
│ └──────────────────────────────────┘ │
└──────────────────────────────────────┘
```

### 5. Add Contact Dialog
```
        ┌───────────────────────┐
        │                       │
        │        👤➕           │
        │                       │
        │ Add Emergency Contact │
        │                       │
        │ ┌───────────────────┐ │
        │ │ Name              │ │
        │ └───────────────────┘ │
        │                       │
        │ ┌───────────────────┐ │
        │ │ Phone Number      │ │
        │ │ +94 77 123 4567   │ │
        │ └───────────────────┘ │
        │                       │
        │ You can also select   │
        │ from your phone       │
        │ contacts              │
        │                       │
        │   [Cancel]   [Add]    │
        └───────────────────────┘
```

### 6. Emergency Confirmation Dialog
```
        ┌───────────────────────┐
        │                       │
        │         📍            │
        │                       │
        │ Send Emergency Alert? │
        │                       │
        │ This will send an SMS │
        │ with your current     │
        │ location to all 3     │
        │ emergency contacts.   │
        │                       │
        │ [Cancel] [Send Alert] │
        └───────────────────────┘
```

## UI Component Details

### Emergency Button States

#### 1. Normal State (Idle)
```
    ┌────────┐
    │        │
    │  SOS   │  ← Red background (#D32F2F)
    │        │     White text
    └────────┘     200dp diameter
```

#### 2. Pressed State (Holding)
```
    ┌────────┐
    │ ◐      │  ← Progress ring animating
    │ HOLD   │     Red container background
    │        │     White text
    └────────┘     3 second animation
```

#### 3. Disabled State
```
    ┌────────┐
    │        │
    │  SOS   │  ← Gray background
    │        │     Gray text
    └────────┘     Not clickable
```

### Status Chips

#### Active GPS
```
┌──────────────────┐
│ 📍 GPS Active    │  ← Green background
└──────────────────┘     Green icon & text
```

#### Inactive GPS
```
┌──────────────────┐
│ 📍 GPS Inactive  │  ← Red background
└──────────────────┘     Red icon & text
```

#### Contacts Status
```
┌─────────────────────┐
│ 👥 3 Emergency      │  ← Green if > 0
│    Contacts         │     Red if = 0
└─────────────────────┘
```

## Color Palette

### Light Theme
```
Primary (Emergency Red):     #D32F2F ███████
Secondary (Warning Amber):   #FFA000 ███████
Tertiary (Safe Green):       #388E3C ███████
Background:                  #FAFAFA ███████
Surface:                     #FFFFFF ███████
Error:                       #D32F2F ███████
```

### Dark Theme
```
Primary (Emergency Red):     #EF5350 ███████
Secondary (Warning Amber):   #FFB300 ███████
Tertiary (Safe Green):       #66BB6A ███████
Background:                  #212121 ███████
Surface:                     #424242 ███████
Error:                       #EF5350 ███████
```

## Navigation Flow

```
     ┌─────────────────┐
     │  MainActivity   │
     └────────┬────────┘
              │
              ▼
     ┌─────────────────┐
     │    NavGraph     │
     └────────┬────────┘
              │
     ┌────────┴────────┬────────────────┐
     │                 │                │
     ▼                 ▼                ▼
┌─────────┐    ┌──────────────┐  ┌──────────┐
│Emergency│◄───┤   Contacts   │  │ Settings │
│ Screen  │    │    Screen    │  │  Screen  │
└────┬────┘    └──────────────┘  └─────▲────┘
     │                                  │
     └──────────────────────────────────┘
```

## Screen Transitions

### From Emergency Screen:
1. **Tap Settings Icon** → Settings Screen (slide in from right)
2. **Tap Manage Contacts** → Contacts Screen (slide in from right)
3. **Hold SOS Button** → Confirmation Dialog (fade in)

### From Contacts Screen:
1. **Tap Back** → Emergency Screen (slide out to right)
2. **Tap FAB** → Add Contact Dialog (fade in)
3. **Tap Delete** → Contact removed (fade out animation)

### From Settings Screen:
1. **Tap Back** → Emergency Screen (slide out to right)
2. **Tap Test Alert** → Test confirmation (dialog)

## Accessibility Features

### Touch Targets
- All buttons: Minimum 48dp x 48dp
- Emergency button: 200dp (extra large)
- Icons: 24dp with 48dp touch area
- Switches: 48dp height

### Content Descriptions
- Emergency button: "Emergency button. Press and hold for 3 seconds to send alert"
- Delete button: "Delete [contact name]"
- Navigation icons: "Back", "Settings"
- Status chips: "GPS Active/Inactive", "X Emergency Contacts"

### Visual Indicators
- High contrast colors (WCAG AA compliant)
- Progress indicator for press-and-hold
- Color coding: Red = error/emergency, Green = success, Amber = warning
- Disabled states clearly indicated

## Typography Hierarchy

```
Display Large (57sp, Bold)     - Not currently used
Display Medium (45sp, Bold)    - Not currently used  
Display Small (36sp, Bold)     - Not currently used

Headline Large (32sp, SemiBold) - Not currently used
Headline Medium (28sp, SemiBold) - Not currently used
Headline Small (24sp, SemiBold) - Dialog titles

Title Large (22sp, SemiBold)    - Screen titles, section headers
Title Medium (16sp, SemiBold)   - Card titles, contact names
Title Small (14sp, Medium)      - Small headers

Body Large (16sp, Normal)       - Primary content, descriptions
Body Medium (14sp, Normal)      - Secondary text, support text
Body Small (12sp, Normal)       - Captions, hints

Label Large (14sp, Medium)      - Button text
Label Medium (12sp, Medium)     - Small button text
Label Small (11sp, Medium)      - Very small labels
```

## Spacing System

```
4dp   - Minimal spacing (between icon and text)
8dp   - Small spacing (within components)
12dp  - Medium spacing (between related items)
16dp  - Standard spacing (screen padding, card padding)
24dp  - Large spacing (between sections)
32dp  - Extra large spacing (major sections)
```

## Component Sizes

```
Emergency Button:    200dp diameter
Contact Avatar:      48dp diameter
Status Chip:         auto width × 36dp height
FAB:                 56dp diameter
Icon Button:         48dp × 48dp
Card Elevation:      2dp
```

## Material Design 3 Components Used

✅ Scaffold - Screen structure
✅ TopAppBar - Navigation and titles
✅ Card - Contact cards, app info
✅ Button - Primary actions
✅ TextButton - Secondary actions
✅ IconButton - Icon actions
✅ FloatingActionButton - Add contact
✅ AlertDialog - Confirmations and forms
✅ TextField/OutlinedTextField - Input fields
✅ Switch - Settings toggles
✅ Icon - Visual indicators
✅ Surface - Status chips, backgrounds
✅ CircularProgressIndicator - Press-and-hold feedback
✅ Divider - Visual separation

## Summary

This visual guide demonstrates:
- 📱 3 main screens with clear purposes
- 🎨 Emergency-appropriate color scheme
- ♿ Accessibility-focused design
- 🔴 Safety features (press-and-hold, confirmation)
- 📐 Material Design 3 principles
- 🎯 Clear visual hierarchy
- 🚀 Professional, production-ready UI

The implementation is complete and ready for integration with ViewModels and business logic.
