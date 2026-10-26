package com.traffigorotta.core.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.theme.RottaTheme

@Composable
fun RottaCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val shape = RottaTheme.shapes.md
    val baseModifier = modifier
        .clip(shape)
        .background(
            color = RottaTheme.colors.surfaceVariant,
            shape = shape
        )

    val finalModifier = if (onClick != null) {
        baseModifier.clickable(onClick = onClick)
    } else {
        baseModifier
    }.padding(20.dp)

    Box(modifier = finalModifier) {
        content()
    }
}
