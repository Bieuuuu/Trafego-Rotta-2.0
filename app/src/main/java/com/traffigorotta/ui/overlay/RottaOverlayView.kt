package com.traffigorotta.ui.overlay

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffigorotta.core.design.component.RottaSignalChip
import com.traffigorotta.core.design.component.SignalStatus
import com.traffigorotta.core.design.theme.RottaTheme
import com.traffigorotta.model.SignalEvaluation
import com.traffigorotta.model.SignalGrade
import com.traffigorotta.service.RideEventType

/**
 * Componente do Overlay Flutuante do Rotta (Fase 2)
 *
 * Segue estritamente as regras do DESIGN.md e AGENTS.md:
 * - Começa como bolha de 52px encostada na lateral com logo "R"
 * - Ao tocar ou surgir corrida (se configurado), expande mostrando ganho da jornada e semáforo
 * - Ocupa no máximo 30% da altura da tela
 * - Nunca fica sobre a área inferior onde apps de corrida colocam botão de aceitar
 * - Mostra status de corrida em andamento e aviso de viagem gravada automaticamente nos relatórios
 */
@Composable
fun RottaOverlayFloatingContent(
    isExpanded: Boolean,
    journeyEarnings: Double,
    evaluation: SignalEvaluation?,
    activeStatus: RideEventType? = null,
    autoReportNotice: String? = null,
    onToggleExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RottaTheme.colors

    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        if (!isExpanded) {
            // Bolha recolhida (52px, circular, preto sólido, letra R)
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .shadow(elevation = 12.dp, shape = CircleShape)
                    .clip(CircleShape)
                    .background(colors.inverseSurface)
                    .border(1.5.dp, colors.outline, CircleShape)
                    .clickable { onToggleExpand() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "R",
                    color = colors.onInverseSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        } else {
            // Painel expandido (cantos lg 28px, inverseSurface, no máximo 30% da tela)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 20.dp, shape = RoundedCornerShape(28.dp))
                    .clip(RoundedCornerShape(28.dp))
                    .background(colors.inverseSurface)
                    .border(1.5.dp, Color(0x40FFFFFF), RoundedCornerShape(28.dp))
                    .clickable { onToggleExpand() }
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "GANHO JORNADA",
                                style = RottaTheme.typography.label,
                                color = colors.onSurfaceMuted
                            )
                            Text(
                                text = "R$ %.2f".format(journeyEarnings),
                                style = RottaTheme.typography.headline,
                                color = colors.onInverseSurface
                            )
                        }

                        if (autoReportNotice != null) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(colors.signalGood)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = autoReportNotice,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.onSignalGood
                                )
                            }
                        } else if (evaluation != null) {
                            val signalStatus = when (evaluation.grade) {
                                SignalGrade.GOOD -> SignalStatus.GOOD
                                SignalGrade.MEDIUM -> SignalStatus.MEDIUM
                                SignalGrade.BAD -> SignalStatus.BAD
                            }

                            RottaSignalChip(
                                status = signalStatus,
                                word = evaluation.grade.label
                            )
                        } else if (activeStatus == RideEventType.ACCEPTED) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(colors.surfaceVariant)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Em Viagem • Passageiro a bordo",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = colors.onInverseSurface
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(colors.outline)
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Aguardando corrida...",
                                    fontSize = 12.sp,
                                    color = colors.onSurfaceMuted
                                )
                            }
                        }
                    }

                    if (evaluation != null) {
                        Spacer(modifier = Modifier.height(14.dp))

                        // Métricas da oferta em Body-sm / números tabulares
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${evaluation.offer.sourceApp}: R$ %.2f (${evaluation.offer.distanceKm} km)".format(evaluation.offer.grossValue),
                                fontSize = 13.sp,
                                color = RottaTheme.colors.onInverseSurface,
                                fontWeight = FontWeight.Medium
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = "R$ %.2f/km".format(evaluation.ratePerKm),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (evaluation.meetsKm) RottaTheme.colors.signalGood else RottaTheme.colors.signalBad
                                )
                                Text(
                                    text = "R$ %.0f/h".format(evaluation.ratePerHour),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (evaluation.meetsHour) RottaTheme.colors.signalGood else RottaTheme.colors.signalBad
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
