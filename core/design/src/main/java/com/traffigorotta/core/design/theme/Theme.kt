package com.traffigorotta.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalRottaColors = staticCompositionLocalOf { LightRottaColors }
val LocalRottaTypography = staticCompositionLocalOf { RottaTypography() }
val LocalRottaShapes = staticCompositionLocalOf { RottaShapes() }
val LocalRottaSpacing = staticCompositionLocalOf { RottaSpacing() }

object RottaTheme {
    val colors: RottaColors
        @Composable
        @ReadOnlyComposable
        get() = LocalRottaColors.current

    val typography: RottaTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalRottaTypography.current

    val shapes: RottaShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalRottaShapes.current

    val spacing: RottaSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalRottaSpacing.current
}

@Composable
fun RottaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val rottaColors = if (darkTheme) DarkRottaColors else LightRottaColors
    val rottaTypography = RottaTypography()
    val rottaShapes = RottaShapes()
    val rottaSpacing = RottaSpacing()

    val materialColors = if (darkTheme) {
        darkColorScheme(
            primary = rottaColors.primary,
            onPrimary = rottaColors.onPrimary,
            surface = rottaColors.surface,
            onSurface = rottaColors.onSurface,
            surfaceVariant = rottaColors.surfaceVariant,
            outline = rottaColors.outline
        )
    } else {
        lightColorScheme(
            primary = rottaColors.primary,
            onPrimary = rottaColors.onPrimary,
            surface = rottaColors.surface,
            onSurface = rottaColors.onSurface,
            surfaceVariant = rottaColors.surfaceVariant,
            outline = rottaColors.outline
        )
    }

    CompositionLocalProvider(
        LocalRottaColors provides rottaColors,
        LocalRottaTypography provides rottaTypography,
        LocalRottaShapes provides rottaShapes,
        LocalRottaSpacing provides rottaSpacing
    ) {
        MaterialTheme(
            colorScheme = materialColors,
            content = content
        )
    }
}
