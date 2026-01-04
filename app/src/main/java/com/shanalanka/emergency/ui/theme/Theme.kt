package com.shanalanka.emergency.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = EmergencyRedDarkTheme,
    onPrimary = Color.White,
    primaryContainer = EmergencyRedDark,
    onPrimaryContainer = Gray200,
    
    secondary = WarningAmberDarkTheme,
    onSecondary = Gray900,
    secondaryContainer = WarningAmberDark,
    onSecondaryContainer = Gray200,
    
    tertiary = SafeGreenDarkTheme,
    onTertiary = Gray900,
    tertiaryContainer = SafeGreenDark,
    onTertiaryContainer = Gray200,
    
    error = EmergencyRedDarkTheme,
    onError = Color.White,
    errorContainer = EmergencyRedDark,
    onErrorContainer = Gray200,
    
    background = Gray900,
    onBackground = Gray100,
    
    surface = Gray800,
    onSurface = Gray100,
    surfaceVariant = Gray700,
    onSurfaceVariant = Gray300,
    
    outline = Gray600,
    outlineVariant = Gray700
)

private val LightColorScheme = lightColorScheme(
    primary = EmergencyRed,
    onPrimary = Color.White,
    primaryContainer = EmergencyRedLight,
    onPrimaryContainer = EmergencyRedDark,
    
    secondary = WarningAmber,
    onSecondary = Color.White,
    secondaryContainer = WarningAmberLight,
    onSecondaryContainer = WarningAmberDark,
    
    tertiary = SafeGreen,
    onTertiary = Color.White,
    tertiaryContainer = SafeGreenLight,
    onTertiaryContainer = SafeGreenDark,
    
    error = EmergencyRed,
    onError = Color.White,
    errorContainer = EmergencyRedLight,
    onErrorContainer = EmergencyRedDark,
    
    background = Gray50,
    onBackground = Gray900,
    
    surface = Color.White,
    onSurface = Gray900,
    surfaceVariant = Gray100,
    onSurfaceVariant = Gray700,
    
    outline = Gray400,
    outlineVariant = Gray300
)

@Composable
fun SahanaLankaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled by default for consistent emergency branding
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}