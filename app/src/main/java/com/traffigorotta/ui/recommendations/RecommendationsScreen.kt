package com.traffigorotta.ui.recommendations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.component.RottaButton
import com.traffigorotta.core.design.component.RottaButtonVariant
import com.traffigorotta.core.design.component.RottaCard
import com.traffigorotta.core.design.theme.RottaTheme

@Composable
fun RecommendationsScreen(
    viewModel: RecommendationsViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = RottaTheme.colors
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Cabeçalho da tela
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "COPILOTO ROTTA • FASE 3",
                    style = RottaTheme.typography.label,
                    color = colors.onSurfaceMuted
                )
                Text(
                    text = "Recomendações",
                    style = RottaTheme.typography.headline,
                    color = colors.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Dicas práticas baseadas no seu histórico real de corridas e custos do veículo.",
            style = RottaTheme.typography.bodySm,
            color = colors.onSurfaceMuted
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Lista de Recomendações
        uiState.recommendations.forEach { rec ->
            RottaCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = rec.category,
                    style = RottaTheme.typography.label,
                    color = colors.onSurfaceMuted
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = rec.title,
                    style = RottaTheme.typography.title,
                    color = colors.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = rec.description,
                    style = RottaTheme.typography.bodySm,
                    color = colors.onSurfaceMuted
                )

                if (rec.dominantStat != null && rec.dominantLabel != null) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.surface, RottaTheme.shapes.sm)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = rec.dominantLabel,
                            style = RottaTheme.typography.label,
                            color = colors.onSurfaceMuted
                        )
                        Text(
                            text = rec.dominantStat,
                            style = RottaTheme.typography.headline,
                            color = colors.onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão secundário de 56dp para voltar na zona do polegar
        RottaButton(
            text = "Voltar ao Início",
            onClick = onNavigateBack,
            variant = RottaButtonVariant.SECONDARY,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}
