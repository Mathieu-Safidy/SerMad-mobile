package com.example.demarches.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = SapphireMid,
    onPrimary = Color(0xFF04202F),
    primaryContainer = SapphireDeep,
    onPrimaryContainer = SapphireLight,
    secondary = GoldMain,
    onSecondary = Color(0xFF251A00),
    secondaryContainer = GoldDark,
    onSecondaryContainer = GoldLight,
    tertiary = MgGreenLight,
    onTertiary = Color(0xFF052E10),
    background = SapphireDark,
    onBackground = Color(0xFFE4EFF8),
    surface = Color(0xFF0B2A41),
    onSurface = Color(0xFFE4EFF8),
    surfaceVariant = Color(0xFF12344F),
    onSurfaceVariant = SapphireLight,
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

private val LightColorScheme = lightColorScheme(
    primary = SapphireMain,
    onPrimary = Color.White,
    primaryContainer = SapphireContainer,
    onPrimaryContainer = SapphireDark,
    secondary = GoldDark,
    onSecondary = Color.White,
    secondaryContainer = GoldContainer,
    onSecondaryContainer = Color(0xFF2A1F00),
    tertiary = MgGreen,
    onTertiary = Color.White,
    tertiaryContainer = MgGreenContainer,
    onTertiaryContainer = Color(0xFF0B2E10),
    background = Snow,
    onBackground = Ink,
    surface = WhitePure,
    onSurface = Ink,
    surfaceVariant = Cloud,
    onSurfaceVariant = Slate,
    outline = Color(0xFFB4C0CC),
    outlineVariant = Cloud,
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B)
)

@Composable
fun DemarchesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

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
        shapes = Shapes,
        content = content
    )
}