package com.drivenext.app.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Цвета приложения (по дизайну Figma)
val Primary = Color(0xFF4A1A5C)
val PrimaryDark = Color(0xFF3D1549)
val PrimaryLight = Color(0xFF6B2C7A)

val Secondary = Color(0xFFFF6B6B)
val SecondaryDark = Color(0xFFE55555)
val SecondaryLight = Color(0xFFFF8A8A)

val Accent = Color(0xFF4ECDC4)
val AccentLight = Color(0xFF7ED6CF)

val Background = Color(0xFFFFFFFF)
val Surface = Color(0xFFF8F9FA)
val SurfaceVariant = Color(0xFFF5F5F7)
val Error = Color(0xFFFF3B30)
val Success = Color(0xFF34C759)

val TextPrimary = Color(0xFF2C2C2E)
val TextSecondary = Color(0xFF8E8E93)
val TextTertiary = Color(0xFFC7C7CC)
val TextOnPrimary = Color(0xFFFFFFFF)
val TextHint = Color(0xFFAEAEB2)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = TextOnPrimary,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = TextPrimary,
    
    secondary = Secondary,
    onSecondary = TextOnPrimary,
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = TextPrimary,
    
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    
    error = Error,
    onError = TextOnPrimary,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = Color.Black,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = TextOnPrimary,
    
    secondary = SecondaryLight,
    onSecondary = Color.Black,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = TextOnPrimary,
    
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    
    error = Error,
    onError = TextOnPrimary,
)

@Composable
fun DriveNextTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DriveNextTypography,
        content = content
    )
}
