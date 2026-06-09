package com.umar.taskmanager.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = TaskTeal,
    secondary = TaskAmber,
    tertiary = TaskTealDark,
    background = TaskBackground,
    surface = TaskSurface,
    onPrimary = TaskSurface,
    onSecondary = TaskInk,
    onBackground = TaskInk,
    onSurface = TaskInk,
    outline = TaskLine,
    error = TaskDanger
)

private val LightColorScheme = lightColorScheme(
    primary = TaskTeal,
    secondary = TaskAmber,
    tertiary = TaskTealDark,
    background = TaskBackground,
    surface = TaskSurface,
    onPrimary = TaskSurface,
    onSecondary = TaskInk,
    onBackground = TaskInk,
    onSurface = TaskInk,
    outline = TaskLine,
    error = TaskDanger
)

@Composable
fun TaskManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
