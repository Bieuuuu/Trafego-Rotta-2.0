package com.traffigorotta.core.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.theme.RottaTheme

enum class SignalLevel(
    val label: String
) {
    GOOD("Vale"),
    MEDIUM("Neutra"),
    BAD("Fraca")
}

@Composable
fun RottaSignalChip(
    level: SignalLevel,
    modifier: Modifier = Modifier,
    customWord: String? = null
) {
    val word = customWord ?: level.label
    val (backgroundColor, textColor) = when (level) {
        SignalLevel.GOOD -> RottaTheme.colors.signalGood to RottaTheme.colors.onSignalGood
        SignalLevel.MEDIUM -> RottaTheme.colors.signalMedium to RottaTheme.colors.onSignalMedium
        SignalLevel.BAD -> RottaTheme.colors.signalBad to RottaTheme.colors.onSignalBad
    }

    Box(
        modifier = modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(backgroundColor, CircleShape)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = word,
            style = RottaTheme.typography.title,
            color = textColor
        )
    }
}
