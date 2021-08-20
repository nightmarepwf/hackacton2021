package com.qavan.app.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    surface = ColorLightSurface,
    onSurface = ColorLightOnSurface,
    primary = ColorLightPrimary,
    secondary = ColorLightSecondary,
    secondaryVariant = ColorLightSecondaryVariant,
)

private val DarkColorPalette = darkColors(
    surface = ColorDarkSurface,
    onSurface = ColorDarkOnSurface,
    primary = ColorDarkPrimary,
    secondary = ColorDarkSecondary,
    secondaryVariant = ColorDarkSecondaryVariant,
)


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
