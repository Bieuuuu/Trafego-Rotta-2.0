package com.traffigorotta.ui.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.component.RottaCard
import com.traffigorotta.core.design.component.RottaSecondaryButton
import com.traffigorotta.core.design.component.RottaStatHero
import com.traffigorotta.core.design.theme.RottaTheme
import com.traffigorotta.model.HourlyEarningsSlot
import com.traffigorotta.model.ReportPeriod

@Composable
fun ReportsScreen(
    viewModel: ReportsViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val summary = state.summary

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RottaTheme.colors.surface)
            .verticalScroll(scrollState)
            .padding(horizontal = RottaTheme.spacing.gutter, vertical = RottaTheme.spacing.lg),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Cabeçalho da tela de relatórios
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ANÁLISE DE GANHOS",
                        style = RottaTheme.typography.label,
                        color = RottaTheme.colors.onSurfaceMuted
                    )
                    Text(
                        text = "Relatórios de Desempenho",
                        style = RottaTheme.typography.headline,
                        color = RottaTheme.colors.onSurface
                    )
                }
                RottaSecondaryButton(
                    text = "Voltar",
                    onClick = onNavigateBack,
                    modifier = Modifier.height(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

            // 1. SELETOR DE PERÍODO (Hoje, Semana, Mês)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RottaTheme.spacing.xs)
            ) {
                ReportPeriod.values().forEach { period ->
                    val isSelected = state.selectedPeriod == period
                    RottaSecondaryButton(
                        text = if (isSelected) "● ${period.label}" else period.label,
                        onClick = { viewModel.selectPeriod(period) },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

            // 2. NÚMERO DOMINANTE: Total do Período
            if (summary != null) {
                RottaCard {
                    val grossFormatted = "R$ %.2f".format(summary.totalGross).replace('.', ',')
                    val netFormatted = "R$ %.2f".format(summary.totalNet).replace('.', ',')
                    val hoursFormatted = "%.1fh online".format(summary.totalHours)

                    RottaStatHero(
                        label = "FATURAMENTO • ${summary.period.label.uppercase()}",
                        value = grossFormatted,
                        subtext = "lucro líquido no bolso: $netFormatted • $hoursFormatted"
                    )
                }

                Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

                // 3. BLOCO: RESUMO OPERACIONAL DO PERÍODO
                Text(
                    text = "MÉTRICAS OPERACIONAIS",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                RottaCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "MÉDIA POR HORA",
                                style = RottaTheme.typography.label,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "R$ %.2f/h".format(summary.averagePerHour).replace('.', ','),
                                style = RottaTheme.typography.title,
                                color = RottaTheme.colors.onSurface
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "TOTAL DE CORRIDAS",
                                style = RottaTheme.typography.label,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "${summary.completedRides}",
                                style = RottaTheme.typography.title,
                                color = RottaTheme.colors.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "KM TOTAL RODADO",
                                style = RottaTheme.typography.label,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "%.1f km".format(summary.totalDistanceKm),
                                style = RottaTheme.typography.title,
                                color = RottaTheme.colors.onSurface
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "CUSTO ESTIMADO CARRO",
                                style = RottaTheme.typography.label,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "- R$ %.2f".format(summary.totalVehicleCost).replace('.', ','),
                                style = RottaTheme.typography.title,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

                // 4. BLOCO: GANHOS POR HORÁRIO (QUANDO GANHOU MAIS OU MENOS)
                Text(
                    text = "GANHOS POR HORÁRIO (R$/HORA)",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                RottaCard {
                    val maxPerHour = summary.hourlySlots.maxOfOrNull { it.earningsPerHour } ?: 60.0

                    Column(verticalArrangement = Arrangement.spacedBy(RottaTheme.spacing.md)) {
                        summary.hourlySlots.forEach { slot ->
                            HourlySlotBar(slot = slot, maxPerHour = maxPerHour)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

                // 5. BLOCO: INSIGHT OPERACIONAL DO MOTORISTA
                Text(
                    text = "ANÁLISE DE EFICIÊNCIA",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                RottaCard {
                    val best = summary.hourlySlots.maxByOrNull { it.earningsPerHour }
                    val lowest = summary.hourlySlots.minByOrNull { it.earningsPerHour }

                    Column(verticalArrangement = Arrangement.spacedBy(RottaTheme.spacing.sm)) {
                        if (best != null) {
                            Text(
                                text = "★ Melhor horário: ${best.timeRange} (R$ %.2f/h)".format(best.earningsPerHour).replace('.', ','),
                                style = RottaTheme.typography.bodySm,
                                fontWeight = FontWeight.SemiBold,
                                color = RottaTheme.colors.onSurface
                            )
                        }
                        if (lowest != null) {
                            Text(
                                text = "⚠️ Horário menos rentável: ${lowest.timeRange} (R$ %.2f/h)".format(lowest.earningsPerHour).replace('.', ','),
                                style = RottaTheme.typography.bodySm,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                        }
                        Text(
                            text = "Dica: Focar no pico da manhã (06h-09h) e tarde (17h-20h) aumenta seu rendimento em até 40%.",
                            style = RottaTheme.typography.bodySm,
                            color = RottaTheme.colors.onSurfaceMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.xl))
    }
}

@Composable
private fun HourlySlotBar(
    slot: HourlyEarningsSlot,
    maxPerHour: Double,
    modifier: Modifier = Modifier
) {
    val fraction = if (maxPerHour > 0) (slot.earningsPerHour / maxPerHour).coerceIn(0.1, 1.0).toFloat() else 0.5f

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = slot.timeRange,
                style = RottaTheme.typography.bodySm,
                fontWeight = FontWeight.Medium,
                color = RottaTheme.colors.onSurface
            )
            Text(
                text = "R$ %.2f/h".format(slot.earningsPerHour).replace('.', ','),
                style = RottaTheme.typography.bodySm,
                fontWeight = FontWeight.SemiBold,
                color = RottaTheme.colors.onSurface
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Barra sólida monocromática proporcional
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RottaTheme.shapes.full)
                .background(RottaTheme.colors.outline)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .height(6.dp)
                    .clip(RottaTheme.shapes.full)
                    .background(RottaTheme.colors.primary)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${slot.completedRides} corridas concluídas",
                style = RottaTheme.typography.label,
                color = RottaTheme.colors.onSurfaceMuted
            )
            Text(
                text = "Total: R$ %.2f".format(slot.grossEarnings).replace('.', ','),
                style = RottaTheme.typography.label,
                color = RottaTheme.colors.onSurfaceMuted
            )
        }
    }
}
