package com.traffigorotta

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.component.RottaCard
import com.traffigorotta.core.design.component.RottaOverlayBubble
import com.traffigorotta.core.design.component.RottaOverlayPanel
import com.traffigorotta.core.design.component.RottaPrimaryButton
import com.traffigorotta.core.design.component.RottaSecondaryButton
import com.traffigorotta.core.design.component.RottaSignalChip
import com.traffigorotta.core.design.component.RottaStatHero
import com.traffigorotta.core.design.component.SignalLevel
import com.traffigorotta.core.design.theme.RottaTheme

@Composable
fun CatalogScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToProfile: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var overlayExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RottaTheme.colors.surface)
            .verticalScroll(scrollState)
            .padding(horizontal = RottaTheme.spacing.gutter, vertical = RottaTheme.spacing.lg)
    ) {
        // Cabeçalho da tela de catálogo com alternância Claro / Escuro
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "TRAFEGO ROTTA",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Text(
                    text = "Catálogo Fase 1",
                    style = RottaTheme.typography.headline,
                    color = RottaTheme.colors.onSurface
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RottaSecondaryButton(
                    text = "Perfil",
                    onClick = onNavigateToProfile,
                    modifier = Modifier.height(44.dp)
                )
                RottaSecondaryButton(
                    text = if (isDarkTheme) "Claro" else "Escuro",
                    onClick = onToggleTheme,
                    modifier = Modifier.height(44.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

        // 1. Hero de Ganho (Número dominante com tnum)
        Text(
            text = "1. NÚMERO DOMINANTE (STAT HERO)",
            style = RottaTheme.typography.label,
            color = RottaTheme.colors.onSurfaceMuted
        )
        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
        RottaCard {
            RottaStatHero(
                label = "GANHO HOJE",
                value = "R$ 485,20",
                subtext = "meta diária: 97% • 6h 10m conectado"
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

        // 2. Semáforo (Cor + Palavra obrigatório)
        Text(
            text = "2. SEMÁFORO (COR + PALAVRA)",
            style = RottaTheme.typography.label,
            color = RottaTheme.colors.onSurfaceMuted
        )
        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
        RottaCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RottaSignalChip(level = SignalLevel.GOOD)
                RottaSignalChip(level = SignalLevel.MEDIUM)
                RottaSignalChip(level = SignalLevel.BAD)
            }
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

        // 3. Botões de Ação Primária e Secundária (56dp altura, área do polegar)
        Text(
            text = "3. BOTÕES (56DP DE ALTURA)",
            style = RottaTheme.typography.label,
            color = RottaTheme.colors.onSurfaceMuted
        )
        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
        Column(verticalArrangement = Arrangement.spacedBy(RottaTheme.spacing.sm)) {
            RottaPrimaryButton(
                text = "Iniciar jornada",
                onClick = {}
            )
            RottaSecondaryButton(
                text = "Encerrar jornada • 4h 12m",
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

        // 4. Cartão com barra discreta de meta
        Text(
            text = "4. CARTÃO COM META (SEM GRADIENTES)",
            style = RottaTheme.typography.label,
            color = RottaTheme.colors.onSurfaceMuted
        )
        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
        RottaCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "META DO DIA",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Text(
                    text = "R$ 485 / R$ 500",
                    style = RottaTheme.typography.bodySm,
                    color = RottaTheme.colors.onSurface
                )
            }
            Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
            // Barra de progresso sólida sem gradientes
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RottaTheme.shapes.full)
                    .background(RottaTheme.colors.outline)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.97f)
                        .height(4.dp)
                        .clip(RottaTheme.shapes.full)
                        .background(RottaTheme.colors.primary)
                )
            }
            Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
            Text(
                text = "Faltam apenas R$ 15 para bater a meta.",
                style = RottaTheme.typography.bodySm,
                color = RottaTheme.colors.onSurfaceMuted
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

        // 5. Overlay sobre 3 fundos simulados (Claro, Escuro e Colorido)
        Text(
            text = "5. OVERLAY SOBRE 3 FUNDOS SIMULADOS",
            style = RottaTheme.typography.label,
            color = RottaTheme.colors.onSurfaceMuted
        )
        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))

        // Fundo A: Claro
        OverlayPreviewSection(
            title = "A. Fundo Claro (Modo Dia Uber/99)",
            backgroundModifier = Modifier.background(Color.White)
        )

        Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

        // Fundo B: Escuro
        OverlayPreviewSection(
            title = "B. Fundo Escuro (Navegação Noturna)",
            backgroundModifier = Modifier.background(Color(0xFF09090B))
        )

        Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

        // Fundo C: Colorido
        OverlayPreviewSection(
            title = "C. Fundo Colorido (Mapa de Trânsito)",
            backgroundModifier = Modifier.background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF2B6CB0), Color(0xFF2C7A7B), Color(0xFFD69E2E))
                )
            )
        )

        Spacer(modifier = Modifier.height(RottaTheme.spacing.xl))
    }
}

@Composable
private fun OverlayPreviewSection(
    title: String,
    backgroundModifier: Modifier,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = RottaTheme.typography.bodySm,
            color = RottaTheme.colors.onSurfaceMuted
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RottaTheme.shapes.md)
                .then(backgroundModifier)
                .border(1.dp, RottaTheme.colors.outline, RottaTheme.shapes.md)
                .padding(16.dp)
        ) {
            if (!expanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RottaOverlayBubble(onClick = { expanded = true })
                    Text(
                        text = "Toque na bolha para expandir",
                        style = RottaTheme.typography.bodySm,
                        color = Color.Gray
                    )
                }
            } else {
                RottaOverlayPanel(
                    journeyEarnings = "R$ 184,20",
                    signalLevel = SignalLevel.GOOD,
                    ratePerKm = "R$ 2,85",
                    ratePerHour = "R$ 48,00",
                    onCollapse = { expanded = false }
                )
            }
        }
    }
}
