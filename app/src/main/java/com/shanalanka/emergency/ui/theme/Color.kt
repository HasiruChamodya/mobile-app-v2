package com.shanalanka.emergency.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Emergency red colors
val EmergencyRed = Color(0xFFD32F2F)
val EmergencyRedDark = Color(0xFFB71C1C)
val EmergencyRedLight = Color(0xFFEF5350)

// Top bar gradient colors
val TopBarGradientStart = Color(0xFFE53935) // Material Red 600 - bright
val TopBarGradientEnd = Color(0xFFB71C1C)   // Material Red 900 - dark

// Top bar gradient brush
val TopBarGradient = Brush.verticalGradient(
    colors = listOf(
        TopBarGradientStart,
        TopBarGradientEnd
    )
)

// Warning amber/yellow colors
val WarningAmber = Color(0xFFFFA000)
val WarningAmberDark = Color(0xFFFF8F00)
val WarningAmberLight = Color(0xFFFFB300)

// Success/Safe green colors
val SafeGreen = Color(0xFF388E3C)
val SafeGreenDark = Color(0xFF2E7D32)
val SafeGreenLight = Color(0xFF66BB6A)

// Neutral grays
val Gray50 = Color(0xFFFAFAFA)
val Gray100 = Color(0xFFF5F5F5)
val Gray200 = Color(0xFFEEEEEE)
val Gray300 = Color(0xFFE0E0E0)
val Gray400 = Color(0xFFBDBDBD)
val Gray500 = Color(0xFF9E9E9E)
val Gray600 = Color(0xFF757575)
val Gray700 = Color(0xFF616161)
val Gray800 = Color(0xFF424242)
val Gray900 = Color(0xFF212121)

// Dark theme colors
val EmergencyRedDarkTheme = Color(0xFFEF5350)
val WarningAmberDarkTheme = Color(0xFFFFB300)
val SafeGreenDarkTheme = Color(0xFF66BB6A)