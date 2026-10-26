// Gerado automaticamente por scripts/generate-tokens.js a partir do DESIGN.md
package com.traffigorotta.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.traffigorotta.core.design.R

// Família de fontes Geist embarcada diretamente no aplicativo (res/font/)
val GeistFontFamily = FontFamily(
    Font(R.font.geist_regular, FontWeight.Normal),
    Font(R.font.geist_medium, FontWeight.Medium),
    Font(R.font.geist_semibold, FontWeight.SemiBold),
    Font(R.font.geist_bold, FontWeight.Bold)
)

@Immutable
data class RottaTypography(
    // Display (56px, 600, 1.05 line height, -0.03em, tnum)
    val display: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 56.sp,
        lineHeight = (56 * 1.05).sp,
        letterSpacing = (-0.03).em,
        fontFeatureSettings = "tnum"
    ),
    // Headline (28px, 600, 1.15 line height, -0.02em)
    val headline: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = (28 * 1.15).sp,
        letterSpacing = (-0.02).em
    ),
    // Title (18px, 600, 1.3 line height)
    val title: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = (18 * 1.3).sp
    ),
    // Body (16px, 400, 1.5 line height)
    val body: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = (16 * 1.5).sp
    ),
    // Body-sm (14px, 400, 1.45 line height)
    val bodySm: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = (14 * 1.45).sp
    ),
    // Label (12px, 500, 1.3 line height, 0.06em letter spacing, tnum)
    val label: TextStyle = TextStyle(
        fontFamily = GeistFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = (12 * 1.3).sp,
        letterSpacing = 0.06.em,
        fontFeatureSettings = "tnum"
    )
)
