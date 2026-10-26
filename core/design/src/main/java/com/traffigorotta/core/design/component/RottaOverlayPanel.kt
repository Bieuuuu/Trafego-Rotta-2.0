package com.traffigorotta.core.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.theme.RottaTheme

@Composable
fun RottaOverlayPanel(
    journeyEarnings: String,
    signalLevel: SignalLevel?,
    ratePerKm: String?,
    ratePerHour: String?,
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RottaTheme.shapes.lg // 28px
    val outlineBorderColor = if (RottaTheme.colors.isDark) {
        Color(0x33FFFFFF)
    } else {
        Color(0x22000000)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 12.dp, shape = shape)
            .clip(shape)
            .background(RottaTheme.colors.inverseSurface, shape)
            .border(width = 1.dp, color = outlineBorderColor, shape = shape)
            .clickable(onClick = onCollapse)
            .padding(16.dp)
    ) {
        // Top row: Ganho da jornada em Headline + Rótulo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "GANHO JORNADA",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onInverseSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = journeyEarnings,
                    style = RottaTheme.typography.headline,
                    color = RottaTheme.colors.onInverseSurface
                )
            }
            if (signalLevel != null) {
                RottaSignalChip(level = signalLevel)
            }
        }

        // Secondary metrics: apenas R$/km e R$/h se corrida detectada
        if (ratePerKm != null || ratePerHour != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (ratePerKm != null) {
                    Text(
                        text = "R$/KM: $ratePerKm",
                        style = RottaTheme.typography.bodySm,
                        color = RottaTheme.colors.onInverseSurface.copy(alpha = 0.9f)
                    )
                }
                if (ratePerHour != null) {
                    Text(
                        text = "R$/H: $ratePerHour",
                        style = RottaTheme.typography.bodySm,
                        color = RottaTheme.colors.onInverseSurface.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}
