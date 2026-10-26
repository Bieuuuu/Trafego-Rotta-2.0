package com.traffigorotta.ui.journey

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.component.RottaCard
import com.traffigorotta.core.design.component.RottaPrimaryButton
import com.traffigorotta.core.design.component.RottaSecondaryButton
import com.traffigorotta.core.design.component.RottaStatHero
import com.traffigorotta.core.design.theme.RottaTheme

@Composable
fun JourneyScreen(
    viewModel: JourneyViewModel,
    onNavigateToProfile: () -> Unit,
    onNavigateToCatalog: () -> Unit,
    onNavigateToManualEntry: () -> Unit,
    onNavigateToGoals: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToTrafficLight: () -> Unit = {},
    onNavigateToRecommendations: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RottaTheme.colors.surface)
            .verticalScroll(scrollState)
            .padding(horizontal = RottaTheme.spacing.gutter, vertical = RottaTheme.spacing.lg),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Cabeçalho da tela de jornada
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (state.isJourneyActive) "JORNADA EM ANDAMENTO" else "JORNADA PAUSADA",
                        style = RottaTheme.typography.label,
                        color = RottaTheme.colors.onSurfaceMuted
                    )
                    Text(
                        text = if (state.isJourneyActive) "Copiloto Ativo" else "Pronto para rodar",
                        style = RottaTheme.typography.headline,
                        color = RottaTheme.colors.onSurface
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    RottaSecondaryButton(
                        text = "Dicas",
                        onClick = onNavigateToRecommendations,
                        modifier = Modifier.height(40.dp)
                    )
                    RottaSecondaryButton(
                        text = "Semáforo",
                        onClick = onNavigateToTrafficLight,
                        modifier = Modifier.height(40.dp)
                    )
                    RottaSecondaryButton(
                        text = "Relatórios",
                        onClick = onNavigateToReports,
                        modifier = Modifier.height(40.dp)
                    )
                    RottaSecondaryButton(
                        text = "Metas",
                        onClick = onNavigateToGoals,
                        modifier = Modifier.height(40.dp)
                    )
                    RottaSecondaryButton(
                        text = "Lançar",
                        onClick = onNavigateToManualEntry,
                        modifier = Modifier.height(40.dp)
                    )
                    RottaSecondaryButton(
                        text = "Perfil",
                        onClick = onNavigateToProfile,
                        modifier = Modifier.height(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

            // 1. NÚMERO DOMINANTE: Ganho da jornada em Display (56px)
            RottaCard {
                if (state.isJourneyActive) {
                    val durationText = "${state.elapsedHours}h ${state.elapsedMinutes}m online"
                    val ridesText = "${state.activeJourney?.completedRidesCount ?: 0} corridas"
                    RottaStatHero(
                        label = "GANHO DA JORNADA",
                        value = state.formattedGrossEarnings,
                        subtext = "$durationText • $ridesText"
                    )
                } else {
                    RottaStatHero(
                        label = "GANHO HOJE",
                        value = state.formattedGrossEarnings,
                        subtext = "Toque abaixo para iniciar a jornada"
                    )
                }
            }

            Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

            // 2. BLOCO OPERACIONAL (Duração, Km rodado, Média R$/h)
            if (state.isJourneyActive) {
                Text(
                    text = "DESEMPENHO DA SESSÃO",
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
                                text = "TEMPO ONLINE",
                                style = RottaTheme.typography.label,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "${state.elapsedHours}h ${state.elapsedMinutes}m",
                                style = RottaTheme.typography.title,
                                color = RottaTheme.colors.onSurface
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "DISTÂNCIA",
                                style = RottaTheme.typography.label,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "%.1f km".format(state.activeJourney?.distanceKm ?: 0.0),
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
                                text = "MÉDIA POR HORA",
                                style = RottaTheme.typography.label,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "R$ %.2f/h".format(state.activeJourney?.earningsPerHour ?: 0.0).replace('.', ','),
                                style = RottaTheme.typography.title,
                                color = RottaTheme.colors.onSurface
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "MÉDIA POR KM",
                                style = RottaTheme.typography.label,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "R$ %.2f/km".format(state.activeJourney?.earningsPerKm ?: 0.0).replace('.', ','),
                                style = RottaTheme.typography.title,
                                color = RottaTheme.colors.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

                // 3. BLOCO FINANCEIRO LÍQUIDO (Descontando custo do carro)
                Text(
                    text = "LUCRO ESTIMADO NO BOLSO",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                RottaCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "LUCRO LÍQUIDO REAL",
                                style = RottaTheme.typography.label,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "R$ %.2f".format(state.estimatedNetProfit).replace('.', ','),
                                style = RottaTheme.typography.headline,
                                color = RottaTheme.colors.onSurface
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "CUSTO CARRO",
                                style = RottaTheme.typography.label,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "- R$ %.2f".format(state.estimatedVehicleCost).replace('.', ','),
                                style = RottaTheme.typography.bodySm,
                                color = RottaTheme.colors.onSurfaceMuted
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

                // 4. MOTOR DETERMINÍSTICO DE PARIDADE DE COMBUSTÍVEL
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "COMBUSTÍVEL DA JORNADA",
                        style = RottaTheme.typography.label,
                        color = RottaTheme.colors.onSurfaceMuted
                    )
                    Text(
                        text = "${state.fuelParity.recommendedLabel} compensa mais hoje",
                        style = RottaTheme.typography.label,
                        color = RottaTheme.colors.signalGood,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                RottaCard {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "TANQUE DE HOJE",
                                    style = RottaTheme.typography.label,
                                    color = RottaTheme.colors.onSurfaceMuted
                                )
                                Text(
                                    text = "Rodando com ${state.activeFuel.label}",
                                    style = RottaTheme.typography.title,
                                    color = RottaTheme.colors.onSurface
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                RottaSecondaryButton(
                                    text = "Etanol",
                                    onClick = { viewModel.selectActiveFuel(com.traffigorotta.model.FuelType.ETANOL) },
                                    modifier = Modifier.height(34.dp)
                                )
                                RottaSecondaryButton(
                                    text = "Gasolina",
                                    onClick = { viewModel.selectActiveFuel(com.traffigorotta.model.FuelType.GASOLINA) },
                                    modifier = Modifier.height(34.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Etanol: R$ %.2f/km".format(state.fuelParity.ethanolCostPerKm).replace('.', ','),
                                style = RottaTheme.typography.bodySm,
                                color = if (state.fuelParity.recommendedFuel == com.traffigorotta.model.FuelType.ETANOL) RottaTheme.colors.signalGood else RottaTheme.colors.onSurfaceMuted
                            )
                            Text(
                                text = "Gasolina: R$ %.2f/km".format(state.fuelParity.gasolineCostPerKm).replace('.', ','),
                                style = RottaTheme.typography.bodySm,
                                color = if (state.fuelParity.recommendedFuel == com.traffigorotta.model.FuelType.GASOLINA) RottaTheme.colors.signalGood else RottaTheme.colors.onSurfaceMuted
                            )
                        }

                        Text(
                            text = "Paridade real do seu carro: ${state.fuelParity.realParityPercentageFormatted} • Bomba: ${state.fuelParity.pumpRatioPercentageFormatted}",
                            style = RottaTheme.typography.caption,
                            color = RottaTheme.colors.onSurfaceMuted
                        )
                    }
                }
            } else if (state.lastCompletedJourney != null) {
                // Resumo da última jornada encerrada
                Text(
                    text = "ÚLTIMA JORNADA CONCLUÍDA",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                RottaCard {
                    val last = state.lastCompletedJourney!!
                    Text(
                        text = "Total bruto: R$ %.2f".format(last.grossEarnings).replace('.', ','),
                        style = RottaTheme.typography.title,
                        color = RottaTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                    Text(
                        text = "${last.completedRidesCount} corridas • %.1f km rodados".format(last.distanceKm),
                        style = RottaTheme.typography.bodySm,
                        color = RottaTheme.colors.onSurfaceMuted
                    )
                }
            }
        }

        // CONFIRMAÇÃO DE ENCERRAMENTO OU BOTÃO PRIMÁRIO
        Column(modifier = Modifier.padding(top = RottaTheme.spacing.xl)) {
            if (state.showEndConfirmation) {
                // Cartão de confirmação de encerramento
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RottaTheme.shapes.md)
                        .background(RottaTheme.colors.surfaceVariant)
                        .border(1.dp, RottaTheme.colors.outline, RottaTheme.shapes.md)
                        .padding(RottaTheme.spacing.md)
                ) {
                    Column {
                        Text(
                            text = "ENCERRAR JORNADA?",
                            style = RottaTheme.typography.label,
                            color = RottaTheme.colors.onSurfaceMuted
                        )
                        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                        Text(
                            text = "Confirma o encerramento da sessão de hoje?",
                            style = RottaTheme.typography.title,
                            color = RottaTheme.colors.onSurface
                        )
                        Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
                        Text(
                            text = "Ganho bruto: ${state.formattedGrossEarnings} • Lucro líquido estimado: R$ %.2f".format(state.estimatedNetProfit).replace('.', ','),
                            style = RottaTheme.typography.bodySm,
                            color = RottaTheme.colors.onSurfaceMuted
                        )

                        Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

                        RottaPrimaryButton(
                            text = "Confirmar encerramento",
                            onClick = { viewModel.confirmEndJourney() }
                        )
                        Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
                        RottaSecondaryButton(
                            text = "Voltar à jornada",
                            onClick = { viewModel.cancelEndJourney() }
                        )
                    }
                }
            } else if (state.isJourneyActive) {
                // Ação de encerrar
                RottaPrimaryButton(
                    text = "Encerrar jornada",
                    onClick = { viewModel.requestEndJourney() }
                )
            } else {
                // Ação de iniciar
                RottaPrimaryButton(
                    text = "Iniciar jornada",
                    onClick = { viewModel.startJourney() }
                )
            }
        }
    }
}
