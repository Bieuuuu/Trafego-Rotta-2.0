// Gerado automaticamente por scripts/generate-tokens.js a partir do DESIGN.md
package com.traffigorotta.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

// Light mode tokens - DESIGN.md
val RottaBlack = Color(0xFF0A0A0A)
val RottaWhite = Color(0xFFFFFFFF)
val RottaSurfaceVariantLight = Color(0xFFF4F4F5)
val RottaMutedLight = Color(0xFF5F5F66)
val RottaOutlineLight = Color(0xFFE4E4E7)
val RottaInverseSurfaceLight = Color(0xFF0A0A0A)
val RottaOnInverseSurfaceLight = Color(0xFFFAFAFA)

// Dark mode tokens
val RottaBackgroundDark = Color(0xFF0A0A0A)
val RottaSurfaceDark = Color(0xFF0A0A0A)
val RottaSurfaceVariantDark = Color(0xFF18181B)
val RottaOnSurfaceDark = Color(0xFFFFFFFF)
val RottaMutedDark = Color(0xFFA1A1AA)
val RottaOutlineDark = Color(0xFF27272A)
val RottaInverseSurfaceDark = Color(0xFFFAFAFA)
val RottaOnInverseSurfaceDark = Color(0xFF0A0A0A)

// Signal colors (Semáforo e Metas) - DESIGN.md
val RottaSignalGood = Color(0xFF15803D)
val RottaOnSignalGood = Color(0xFFFFFFFF)

val RottaSignalMedium = Color(0xFFFACC15)
val RottaOnSignalMedium = Color(0xFF0A0A0A)

val RottaSignalBad = Color(0xFFB91C1C)
val RottaOnSignalBad = Color(0xFFFFFFFF)

@Immutable
data class RottaColors(
    val primary: Color,
    val onPrimary: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val onSurface: Color,
    val onSurfaceMuted: Color,
    val outline: Color,
    val inverseSurface: Color,
    val onInverseSurface: Color,
    val signalGood: Color = RottaSignalGood,
    val onSignalGood: Color = RottaOnSignalGood,
    val signalMedium: Color = RottaSignalMedium,
    val onSignalMedium: Color = RottaOnSignalMedium,
    val signalBad: Color = RottaSignalBad,
    val onSignalBad: Color = RottaOnSignalBad,
    val isDark: Boolean = false
)

val LightRottaColors = RottaColors(
    primary = RottaBlack,
    onPrimary = RottaWhite,
    surface = RottaWhite,
    surfaceVariant = RottaSurfaceVariantLight,
    onSurface = RottaBlack,
    onSurfaceMuted = RottaMutedLight,
    outline = RottaOutlineLight,
    inverseSurface = RottaInverseSurfaceLight,
    onInverseSurface = RottaOnInverseSurfaceLight,
    isDark = false
)

val DarkRottaColors = RottaColors(
    primary = Color(0xFFFAFAFA),
    onPrimary = RottaBlack,
    surface = RottaSurfaceDark,
    surfaceVariant = RottaSurfaceVariantDark,
    onSurface = RottaOnSurfaceDark,
    onSurfaceMuted = RottaMutedDark,
    outline = RottaOutlineDark,
    inverseSurface = RottaInverseSurfaceDark,
    onInverseSurface = RottaOnInverseSurfaceDark,
    isDark = true
)
