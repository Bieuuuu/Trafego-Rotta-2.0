package com.traffigorotta.ui.signal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.traffigorotta.core.design.component.RottaButton
import com.traffigorotta.core.design.component.RottaButtonVariant
import com.traffigorotta.core.design.component.RottaCard
import com.traffigorotta.core.design.component.RottaSignalChip
import com.traffigorotta.core.design.component.RottaTextField
import com.traffigorotta.core.design.component.SignalStatus
import com.traffigorotta.core.design.theme.RottaTheme
import com.traffigorotta.model.SignalGrade
import com.traffigorotta.service.RottaOverlayService

@Composable
fun TrafficLightSettingsScreen(
    viewModel: TrafficLightViewModel,
    onOpenDisclosure: () -> Unit,
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val colors = RottaTheme.colors
    val scrollState = rememberScrollState()

    var minKmInput by remember(uiState.config.minRatePerKm) {
        mutableStateOf("%.2f".format(uiState.config.minRatePerKm).replace(',', '.'))
    }
    var minMinInput by remember(uiState.config.minRatePerMinute) {
        mutableStateOf("%.2f".format(uiState.config.minRatePerMinute).replace(',', '.'))
    }
    var minHourInput by remember(uiState.config.minRatePerHour) {
        mutableStateOf("%.2f".format(uiState.config.minRatePerHour).replace(',', '.'))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Semáforo e Overlay",
                    style = RottaTheme.typography.headline,
                    color = colors.onSurface
                )
                Text(
                    text = "Classificação instantânea no aparelho ao receber uma oferta.",
                    style = RottaTheme.typography.bodySm,
                    color = colors.onSurfaceMuted
                )
            }
            RottaButton(
                text = "Voltar",
                onClick = onNavigateBack,
                variant = RottaButtonVariant.SECONDARY,
                modifier = Modifier.height(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card de Configuração dos Mínimos
        RottaCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "VALORES MÍNIMOS ACEITÁVEIS",
                style = RottaTheme.typography.label,
                color = colors.onSurfaceMuted
            )

            Spacer(modifier = Modifier.height(16.dp))

            RottaTextField(
                label = "Mínimo por Km",
                value = minKmInput,
                onValueChange = { minKmInput = it },
                prefix = "R$",
                suffix = "/km",
                placeholder = "2.20"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    RottaTextField(
                        label = "Por Minuto",
                        value = minMinInput,
                        onValueChange = { minMinInput = it },
                        prefix = "R$",
                        suffix = "/min",
                        placeholder = "0.60"
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    RottaTextField(
                        label = "Por Hora",
                        value = minHourInput,
                        onValueChange = { minHourInput = it },
                        prefix = "R$",
                        suffix = "/h",
                        placeholder = "36.00"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            RottaButton(
                text = "Salvar Critérios",
                onClick = {
                    val km = minKmInput.replace(',', '.').toDoubleOrNull() ?: uiState.config.minRatePerKm
                    val min = minMinInput.replace(',', '.').toDoubleOrNull() ?: uiState.config.minRatePerMinute
                    val hour = minHourInput.replace(',', '.').toDoubleOrNull() ?: uiState.config.minRatePerHour
                    viewModel.updateMinRates(km, min, hour)
                },
                variant = RottaButtonVariant.PRIMARY,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Opções do Overlay Flutuante
        RottaCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "COMPORTAMENTO DO OVERLAY",
                style = RottaTheme.typography.label,
                color = colors.onSurfaceMuted
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Switch Overlay Ativo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Bolha Flutuante Ativa",
                        style = RottaTheme.typography.title,
                        color = colors.onSurface
                    )
                    Text(
                        text = "Exibe o ganho da jornada flutuando sobre outros apps",
                        style = RottaTheme.typography.bodySm,
                        color = colors.onSurfaceMuted
                    )
                }

                Switch(
                    checked = uiState.config.overlayEnabled,
                    onCheckedChange = { enabled ->
                        viewModel.toggleOverlayEnabled(enabled)
                        if (enabled) {
                            RottaOverlayService.start(context)
                        } else {
                            RottaOverlayService.stop(context)
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colors.onPrimary,
                        checkedTrackColor = colors.primary,
                        uncheckedThumbColor = colors.onSurfaceMuted,
                        uncheckedTrackColor = colors.surfaceVariant
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Switch Auto Expand
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Expandir ao Surgir Corrida",
                        style = RottaTheme.typography.title,
                        color = colors.onSurface
                    )
                    Text(
                        text = "Abre o semáforo automaticamente durante o toque da oferta",
                        style = RottaTheme.typography.bodySm,
                        color = colors.onSurfaceMuted
                    )
                }

                Switch(
                    checked = uiState.config.autoExpandOnOffer,
                    onCheckedChange = { viewModel.toggleAutoExpand(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colors.onPrimary,
                        checkedTrackColor = colors.primary,
                        uncheckedThumbColor = colors.onSurfaceMuted,
                        uncheckedTrackColor = colors.surfaceVariant
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Simulador em Tempo Real da Decisão
        RottaCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SIMULADOR DE OFERTAS",
                    style = RottaTheme.typography.label,
                    color = colors.onSurfaceMuted
                )
                Text(
                    text = "Resposta: < 200ms",
                    style = RottaTheme.typography.label,
                    color = colors.onSurfaceMuted
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Presets de teste
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.surface)
                        .border(1.dp, colors.outline, RoundedCornerShape(8.dp))
                        .clickable { viewModel.simulatePreset(0) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Oferta Boa", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = colors.onSurface)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.surface)
                        .border(1.dp, colors.outline, RoundedCornerShape(8.dp))
                        .clickable { viewModel.simulatePreset(1) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Oferta Média", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = colors.onSurface)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.surface)
                        .border(1.dp, colors.outline, RoundedCornerShape(8.dp))
                        .clickable { viewModel.simulatePreset(2) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Oferta Fraca", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = colors.onSurface)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resultado da Avaliação Atual
            uiState.latestEvaluation?.let { eval ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colors.surface)
                        .border(1.5.dp, colors.outline, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "${eval.offer.sourceApp} • ${eval.offer.distanceKm} km (${eval.offer.durationMinutes} min)",
                                    style = RottaTheme.typography.bodySm,
                                    color = colors.onSurfaceMuted
                                )
                                Text(
                                    text = "R$ %.2f".format(eval.offer.grossValue),
                                    style = RottaTheme.typography.headline,
                                    color = colors.onSurface
                                )
                            }

                            val signalStatus = when (eval.grade) {
                                SignalGrade.GOOD -> SignalStatus.GOOD
                                SignalGrade.MEDIUM -> SignalStatus.MEDIUM
                                SignalGrade.BAD -> SignalStatus.BAD
                            }

                            RottaSignalChip(
                                status = signalStatus,
                                word = eval.grade.label
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("R$/KM", style = RottaTheme.typography.label, color = colors.onSurfaceMuted)
                                Text(
                                    text = "R$ %.2f".format(eval.ratePerKm),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = if (eval.meetsKm) colors.signalGood else colors.signalBad
                                )
                            }

                            Column {
                                Text("R$/MIN", style = RottaTheme.typography.label, color = colors.onSurfaceMuted)
                                Text(
                                    text = "R$ %.2f".format(eval.ratePerMin),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = if (eval.meetsMin) colors.signalGood else colors.signalBad
                                )
                            }

                            Column {
                                Text("R$/HORA", style = RottaTheme.typography.label, color = colors.onSurfaceMuted)
                                Text(
                                    text = "R$ %.2f".format(eval.ratePerHour),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = if (eval.meetsHour) colors.signalGood else colors.signalBad
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Link para a tela de Consentimento Destacado
        RottaButton(
            text = "Ver Termo de Transparência de Permissões",
            onClick = onOpenDisclosure,
            variant = RottaButtonVariant.SECONDARY,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}
