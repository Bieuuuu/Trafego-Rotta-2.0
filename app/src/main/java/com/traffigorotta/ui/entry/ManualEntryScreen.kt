package com.traffigorotta.ui.entry

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffigorotta.core.design.component.RottaCard
import com.traffigorotta.core.design.component.RottaPrimaryButton
import com.traffigorotta.core.design.component.RottaSecondaryButton
import com.traffigorotta.core.design.component.RottaStatHero
import com.traffigorotta.core.design.component.RottaTextField
import com.traffigorotta.core.design.theme.RottaTheme
import com.traffigorotta.model.EarningSource
import com.traffigorotta.model.EntryType
import com.traffigorotta.model.ExpenseCategory
import java.time.format.DateTimeFormatter

@Composable
fun ManualEntryScreen(
    viewModel: ManualEntryViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RottaTheme.colors.surface)
            .verticalScroll(scrollState)
            .padding(horizontal = RottaTheme.spacing.gutter, vertical = RottaTheme.spacing.lg),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Cabeçalho da tela
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CONTROLE FINANCEIRO",
                        style = RottaTheme.typography.label,
                        color = RottaTheme.colors.onSurfaceMuted
                    )
                    Text(
                        text = "Ganhos e Despesas",
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

            // 1. NÚMERO DOMINANTE: Saldo Líquido do Dia (56px)
            RottaCard {
                val netFormatted = "R$ %.2f".format(state.netBalance).replace('.', ',')
                val earningsFormatted = "R$ %.2f".format(state.totalEarnings).replace('.', ',')
                val expensesFormatted = "R$ %.2f".format(state.totalExpenses).replace('.', ',')

                RottaStatHero(
                    label = "SALDO LÍQUIDO HOJE",
                    value = netFormatted,
                    subtext = "ganhos: $earningsFormatted • despesas: $expensesFormatted"
                )
            }

            Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

            // Formulário de novo lançamento (quando aberto)
            if (state.isFormOpen) {
                Text(
                    text = "NOVO LANÇAMENTO",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                RottaCard {
                    // Alternador Ganho vs Despesa
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(RottaTheme.spacing.xs)
                    ) {
                        RottaSecondaryButton(
                            text = if (state.entryType == EntryType.EARNING) "● Ganho (+)" else "Ganho (+)",
                            onClick = { viewModel.setEntryType(EntryType.EARNING) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                        )
                        RottaSecondaryButton(
                            text = if (state.entryType == EntryType.EXPENSE) "● Despesa (-)" else "Despesa (-)",
                            onClick = { viewModel.setEntryType(EntryType.EXPENSE) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

                    // Valor
                    RottaTextField(
                        value = state.amountInput,
                        onValueChange = { viewModel.onAmountChange(it) },
                        label = "Valor do lançamento",
                        placeholder = "0,00",
                        prefix = "R$",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )

                    Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))

                    if (state.entryType == EntryType.EARNING) {
                        // Seletor de plataforma de corrida
                        Text(
                            text = "PLATAFORMA",
                            style = RottaTheme.typography.label,
                            color = RottaTheme.colors.onSurfaceMuted
                        )
                        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(RottaTheme.spacing.xs)
                        ) {
                            EarningSource.values().forEach { source ->
                                val isSelected = state.selectedSource == source
                                RottaSecondaryButton(
                                    text = if (isSelected) "● ${source.label}" else source.label,
                                    onClick = { viewModel.onSourceSelect(source) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(42.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))

                        RottaTextField(
                            value = state.distanceKmInput,
                            onValueChange = { viewModel.onDistanceChange(it) },
                            label = "Distância percorrida (opcional)",
                            placeholder = "8.5",
                            suffix = "km",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )
                    } else {
                        // Seletor de categoria de despesa
                        Text(
                            text = "CATEGORIA DA DESPESA",
                            style = RottaTheme.typography.label,
                            color = RottaTheme.colors.onSurfaceMuted
                        )
                        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
                        Column(verticalArrangement = Arrangement.spacedBy(RottaTheme.spacing.xs)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(RottaTheme.spacing.xs)
                            ) {
                                listOf(ExpenseCategory.COMBUSTIVEL, ExpenseCategory.ALIMENTACAO).forEach { cat ->
                                    val isSelected = state.selectedCategory == cat
                                    RottaSecondaryButton(
                                        text = if (isSelected) "● ${cat.label}" else cat.label,
                                        onClick = { viewModel.onCategorySelect(cat) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(42.dp)
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(RottaTheme.spacing.xs)
                            ) {
                                listOf(ExpenseCategory.PEDAGIO, ExpenseCategory.LAVAGEM, ExpenseCategory.MANUTENCAO).forEach { cat ->
                                    val isSelected = state.selectedCategory == cat
                                    RottaSecondaryButton(
                                        text = if (isSelected) "● ${cat.label}" else cat.label,
                                        onClick = { viewModel.onCategorySelect(cat) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(42.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))

                    RottaTextField(
                        value = state.notesInput,
                        onValueChange = { viewModel.onNotesChange(it) },
                        label = "Observação (opcional)",
                        placeholder = "Ex: Corrida dinâmica ou Posto Ipiranga"
                    )

                    if (state.errorMessage != null) {
                        Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
                        Text(
                            text = state.errorMessage!!,
                            style = RottaTheme.typography.bodySm,
                            color = RottaTheme.colors.signalBad
                        )
                    }

                    Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

                    RottaPrimaryButton(
                        text = if (state.entryType == EntryType.EARNING) "Salvar ganho" else "Salvar despesa",
                        onClick = { viewModel.saveEntry() }
                    )

                    Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))

                    RottaSecondaryButton(
                        text = "Cancelar",
                        onClick = { viewModel.toggleForm() },
                        modifier = Modifier.height(44.dp)
                    )
                }

                Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))
            }

            // Mensagem de sucesso
            if (state.successMessage != null) {
                Text(
                    text = state.successMessage!!,
                    style = RottaTheme.typography.bodySm,
                    color = RottaTheme.colors.signalGood,
                    modifier = Modifier.padding(bottom = RottaTheme.spacing.sm)
                )
            }

            // Lista de lançamentos do dia
            Text(
                text = "LANÇAMENTOS DE HOJE (${state.entries.size})",
                style = RottaTheme.typography.label,
                color = RottaTheme.colors.onSurfaceMuted
            )
            Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))

            if (state.entries.isEmpty()) {
                RottaCard {
                    Text(
                        text = "Nenhum lançamento registrado hoje.",
                        style = RottaTheme.typography.bodySm,
                        color = RottaTheme.colors.onSurfaceMuted
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(RottaTheme.spacing.xs)) {
                    state.entries.forEach { entry ->
                        RottaCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    val title = if (entry.type == EntryType.EARNING) {
                                        "${entry.earningSource?.label ?: "Ganho"}"
                                    } else {
                                        "${entry.expenseCategory?.label ?: "Despesa"}"
                                    }
                                    Text(
                                        text = title,
                                        style = RottaTheme.typography.title,
                                        color = RottaTheme.colors.onSurface
                                    )
                                    val sub = if (entry.distanceKm > 0) {
                                        "${entry.timestamp.format(timeFormatter)} • %.1f km".format(entry.distanceKm)
                                    } else if (entry.notes.isNotBlank()) {
                                        "${entry.timestamp.format(timeFormatter)} • ${entry.notes}"
                                    } else {
                                        entry.timestamp.format(timeFormatter)
                                    }
                                    Text(
                                        text = sub,
                                        style = RottaTheme.typography.bodySm,
                                        color = RottaTheme.colors.onSurfaceMuted
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    val isEarning = entry.type == EntryType.EARNING
                                    val prefixSign = if (isEarning) "+ " else "- "
                                    Text(
                                        text = prefixSign + "R$ %.2f".format(entry.amount).replace('.', ','),
                                        style = RottaTheme.typography.title,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isEarning) RottaTheme.colors.onSurface else RottaTheme.colors.onSurfaceMuted
                                    )
                                    Spacer(modifier = Modifier.padding(start = RottaTheme.spacing.sm))
                                    Text(
                                        text = "✕",
                                        style = RottaTheme.typography.bodySm,
                                        color = RottaTheme.colors.onSurfaceMuted,
                                        modifier = Modifier.clickable { viewModel.deleteEntry(entry.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // AÇÃO PRIMÁRIA ÚNICA NA BASE (quando formulário fechado)
        if (!state.isFormOpen) {
            Column(modifier = Modifier.padding(top = RottaTheme.spacing.xl)) {
                RottaPrimaryButton(
                    text = "+ Lançar ganho ou despesa",
                    onClick = { viewModel.toggleForm() }
                )
            }
        }
    }
}
