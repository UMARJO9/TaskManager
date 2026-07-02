package com.umar.taskmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class TmColorScheme(
    val background: Color,
    val surface: Color,
    val surfaceSoft: Color,
    val card: Color,
    val line: Color,
    val ink: Color,
    val inkMuted: Color,
    val hint: Color
)

val LightTmColors = TmColorScheme(
    background = LightBackground,
    surface = LightSurface,
    surfaceSoft = LightSurfaceSoft,
    card = LightCard,
    line = LightDivider,
    ink = LightPrimaryText,
    inkMuted = LightSecondaryText,
    hint = HintText
)

val DarkTmColors = TmColorScheme(
    background = DarkBackground,
    surface = DarkSurface,
    surfaceSoft = DarkSurfaceSoft,
    card = DarkCard,
    line = DarkDivider,
    ink = DarkPrimaryText,
    inkMuted = DarkSecondaryText,
    hint = HintText
)

val LocalTmColors = staticCompositionLocalOf { LightTmColors }

private val LightColorScheme = lightColorScheme(
    primary = ActionPrimary,
    onPrimary = OnAction,
    secondary = SystemInfo,
    onSecondary = OnAction,
    tertiary = SystemSuccess,
    onTertiary = OnAction,
    background = LightBackground,
    onBackground = LightPrimaryText,
    surface = LightSurface,
    onSurface = LightPrimaryText,
    surfaceVariant = LightSurfaceSoft,
    onSurfaceVariant = LightSecondaryText,
    outline = LightDivider,
    error = SystemError,
    onError = OnAction
)

private val DarkColorScheme = darkColorScheme(
    primary = ActionPrimary,
    onPrimary = OnAction,
    secondary = SystemInfo,
    onSecondary = OnAction,
    tertiary = SystemSuccess,
    onTertiary = OnAction,
    background = DarkBackground,
    onBackground = DarkPrimaryText,
    surface = DarkSurface,
    onSurface = DarkPrimaryText,
    surfaceVariant = DarkSurfaceSoft,
    onSurfaceVariant = DarkSecondaryText,
    outline = DarkDivider,
    error = SystemError,
    onError = OnAction
)

@Composable
fun TaskManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val tmColors = if (darkTheme) DarkTmColors else LightTmColors

    CompositionLocalProvider(LocalTmColors provides tmColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
