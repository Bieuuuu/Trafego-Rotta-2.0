package com.traffigorotta.core.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffigorotta.core.design.theme.RottaTheme

@Composable
fun RottaOverlayBubble(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bubbleShape = RottaTheme.shapes.full
    val outlineBorderColor = if (RottaTheme.colors.isDark) {
        Color(0x33FFFFFF)
    } else {
        Color(0x22000000)
    }

    Box(
        modifier = modifier
            .size(52.dp)
            .shadow(elevation = 6.dp, shape = bubbleShape)
            .clip(bubbleShape)
            .background(RottaTheme.colors.inverseSurface, bubbleShape)
            .border(width = 1.dp, color = outlineBorderColor, shape = bubbleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        // Logo minimalista em P&B com 'R' geométrico de alto contraste
        Text(
            text = "R",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = RottaTheme.colors.onInverseSurface
        )
    }
}
