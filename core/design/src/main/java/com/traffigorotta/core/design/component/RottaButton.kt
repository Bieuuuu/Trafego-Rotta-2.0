package com.traffigorotta.core.design.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.theme.RottaTheme

@Composable
fun RottaPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = RottaTheme.shapes.full,
        colors = ButtonDefaults.buttonColors(
            containerColor = RottaTheme.colors.primary,
            contentColor = RottaTheme.colors.onPrimary,
            disabledContainerColor = RottaTheme.colors.outline,
            disabledContentColor = RottaTheme.colors.onSurfaceMuted
        )
    ) {
        Text(
            text = text,
            style = RottaTheme.typography.title
        )
    }
}

@Composable
fun RottaSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = RottaTheme.shapes.full,
        colors = ButtonDefaults.buttonColors(
            containerColor = RottaTheme.colors.surfaceVariant,
            contentColor = RottaTheme.colors.onSurface,
            disabledContainerColor = RottaTheme.colors.surfaceVariant.copy(alpha = 0.5f),
            disabledContentColor = RottaTheme.colors.onSurfaceMuted
        )
    ) {
        Text(
            text = text,
            style = RottaTheme.typography.title
        )
    }
}
