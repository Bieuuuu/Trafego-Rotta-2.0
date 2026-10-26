package com.traffigorotta.core.design.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.theme.RottaTheme

@Composable
fun RottaStatHero(
    label: String,
    value: String,
    context: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        // Rótulo pequeno em caixa alta espaçado
        Text(
            text = label.uppercase(),
            style = RottaTheme.typography.label,
            color = RottaTheme.colors.onSurfaceMuted
        )
        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
        // Valor dominante em Display (56px)
        Text(
            text = value,
            style = RottaTheme.typography.display,
            color = RottaTheme.colors.onSurface
        )
        if (context != null) {
            Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
            // Linha de contexto em Body-sm
            Text(
                text = context,
                style = RottaTheme.typography.bodySm,
                color = RottaTheme.colors.onSurfaceMuted
            )
        }
    }
}
