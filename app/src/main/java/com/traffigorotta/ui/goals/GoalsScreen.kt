package com.traffigorotta.ui.goals

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.component.RottaCard
import com.traffigorotta.core.design.component.RottaPrimaryButton
import com.traffigorotta.core.design.component.RottaSecondaryButton
import com.traffigorotta.core.design.component.RottaStatHero
import com.traffigorotta.core.design.component.RottaTextField
import com.traffigorotta.core.design.theme.RottaTheme
import com.traffigorotta.model.GoalProgress

@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel,
    onNavigateBack: () -> Unit,
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
            // Cabeçalho da tela de metas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "PLANEJAMENTO",
                        style = RottaTheme.typography.label,
                        color = RottaTheme.colors.onSurfaceMuted
                    )
                    Text(
                        text = "Metas Financeiras",
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

            Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

            // 1. NÚMERO DOMINANTE: Meta Diária (% e valor)
            RottaCard {
                val daily = state.dailyProgress
                val subtext = if (daily.isCompleted) {
                    "Meta de hoje alcançada! Parabéns."
                } else {
                    "Faltam apenas R$ %.2f para a meta de hoje".format(daily.remaining).replace('.', ',')
                }

                RottaStatHero(
                    label = "META DO DIA",
                    value = "%.0f%%".format(daily.percentage),
                    subtext = subtext
                )
            }

            Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

            // Formulário de Ajuste das Metas (quando aberto)
            if (state.isEditing) {
                Text(
                    text = "AJUSTAR VALORES DAS METAS",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                RottaCard {
                    RottaTextField(
                        value = state.dailyInput,
                        onValueChange = { viewModel.onDailyInputChange(it) },
                        label = "Meta diária",
                        placeholder = "500",
                        prefix = "R$",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
                    RottaTextField(
                        value = state.weeklyInput,
                        onValueChange = { viewModel.onWeeklyInputChange(it) },
                        label = "Meta semanal",
                        placeholder = "2500",
                        prefix = "R$",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
                    RottaTextField(
                        value = state.monthlyInput,
                        onValueChange = { viewModel.onMonthlyInputChange(it) },
                        label = "Meta mensal",
                        placeholder = "10000",
                        prefix = "R$",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

                    RottaPrimaryButton(
                        text = "Salvar metas",
                        onClick = { viewModel.saveGoals() }
                    )
                    Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                    RottaSecondaryButton(
                        text = "Cancelar",
                        onClick = { viewModel.toggleEdit() },
                        modifier = Modifier.height(44.dp)
                    )
                }

                Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))
            }

            if (state.savedMessage != null) {
                Text(
                    text = state.savedMessage!!,
                    style = RottaTheme.typography.bodySm,
                    color = RottaTheme.colors.signalGood,
                    modifier = Modifier.padding(bottom = RottaTheme.spacing.sm)
                )
            }

            // 2. BLOCO: META DIÁRIA COM BARRA SÓLIDA
            GoalProgressCard(
                title = "META DIÁRIA",
                progress = state.dailyProgress,
                helperText = "Estimativa: ~1 corrida para bater a meta de hoje"
            )

            Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

            // 3. BLOCO: META SEMANAL COM BARRA SÓLIDA
            GoalProgressCard(
                title = "META SEMANAL",
                progress = state.weeklyProgress,
                helperText = "Média necessária: R$ 330/dia nos próximos 2 dias"
            )

            Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

            // 4. BLOCO: META MENSAL COM BARRA SÓLIDA
            GoalProgressCard(
                title = "META MENSAL",
                progress = state.monthlyProgress,
                helperText = "Ritmo atual: 69,2% no dia 20 do mês"
            )
        }

        // AÇÃO PRIMÁRIA ÚNICA NA BASE (quando não estiver editando)
        if (!state.isEditing) {
            Column(modifier = Modifier.padding(top = RottaTheme.spacing.xl)) {
                RottaPrimaryButton(
                    text = "Ajustar valores das metas",
                    onClick = { viewModel.toggleEdit() }
                )
            }
        }
    }
}

@Composable
private fun GoalProgressCard(
    title: String,
    progress: GoalProgress,
    helperText: String,
    modifier: Modifier = Modifier
) {
    RottaCard(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = RottaTheme.typography.label,
                color = RottaTheme.colors.onSurfaceMuted
            )
            Text(
                text = "R$ %.0f / R$ %.0f".format(progress.current, progress.target),
                style = RottaTheme.typography.bodySm,
                fontWeight = FontWeight.SemiBold,
                color = RottaTheme.colors.onSurface
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))

        // Barra de progresso sólida sem gradientes (conforme DESIGN.md)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RottaTheme.shapes.full)
                .background(RottaTheme.colors.outline)
        ) {
            val barColor = if (progress.isCompleted) {
                RottaTheme.colors.signalGood
            } else {
                RottaTheme.colors.primary
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.progressFraction)
                    .height(6.dp)
                    .clip(RottaTheme.shapes.full)
                    .background(barColor)
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = helperText,
                style = RottaTheme.typography.bodySm,
                color = RottaTheme.colors.onSurfaceMuted
            )
            Text(
                text = "%.1f%%".format(progress.percentage),
                style = RottaTheme.typography.bodySm,
                fontWeight = FontWeight.SemiBold,
                color = if (progress.isCompleted) RottaTheme.colors.signalGood else RottaTheme.colors.onSurface
            )
        }
    }
}
