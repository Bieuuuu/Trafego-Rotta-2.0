package com.traffigorotta.ui.profile

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.component.RottaCard
import com.traffigorotta.core.design.component.RottaPrimaryButton
import com.traffigorotta.core.design.component.RottaSecondaryButton
import com.traffigorotta.core.design.component.RottaStatHero
import com.traffigorotta.core.design.component.RottaTextField
import com.traffigorotta.core.design.theme.RottaTheme
import com.traffigorotta.model.FuelType

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToCatalog: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToPrivacy: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var showFixedDetails by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RottaTheme.colors.surface)
            .verticalScroll(scrollState)
            .padding(horizontal = RottaTheme.spacing.gutter, vertical = RottaTheme.spacing.lg)
    ) {
        // Cabeçalho da tela de perfil
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "CONFIGURAÇÃO",
                    style = RottaTheme.typography.label,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Text(
                    text = "Perfil e Carro",
                    style = RottaTheme.typography.headline,
                    color = RottaTheme.colors.onSurface
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                RottaSecondaryButton(
                    text = "Voltar",
                    onClick = onNavigateBack,
                    modifier = Modifier.height(40.dp)
                )
                RottaSecondaryButton(
                    text = "Catálogo",
                    onClick = onNavigateToCatalog,
                    modifier = Modifier.height(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

        // 1. NÚMERO DOMINANTE: Custo total estimado por km rodado
        RottaCard {
            val totalCostFormatted = "R$ %.2f".format(state.calculatedTotalCostPerKm).replace('.', ',')
            val fuelCostFormatted = "R$ %.2f".format(state.calculatedFuelCostPerKm).replace('.', ',')
            val fixedCostFormatted = "R$ %.2f".format(state.calculatedDailyFixedCost).replace('.', ',')

            RottaStatHero(
                label = "CUSTO ESTIMADO POR KM",
                value = "$totalCostFormatted/km",
                subtext = "combustível: $fuelCostFormatted/km • custo fixo: $fixedCostFormatted/dia"
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

        // 2. BLOCO: DADOS DO MOTORISTA
        Text(
            text = "DADOS DO MOTORISTA",
            style = RottaTheme.typography.label,
            color = RottaTheme.colors.onSurfaceMuted
        )
        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
        RottaCard {
            RottaTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = "Nome completo",
                placeholder = "Ex: Carlos Silva"
            )
            Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
            RottaTextField(
                value = state.phone,
                onValueChange = { viewModel.onPhoneChange(it) },
                label = "Telefone / WhatsApp",
                placeholder = "(11) 98765-4321",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
            RottaTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = "E-mail de acesso",
                placeholder = "motorista@rotta.app",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

        // 3. BLOCO: DADOS DO CARRO E CONSUMO
        Text(
            text = "DADOS DO VEÍCULO E CONSUMO",
            style = RottaTheme.typography.label,
            color = RottaTheme.colors.onSurfaceMuted
        )
        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
        RottaCard {
            RottaTextField(
                value = state.carModel,
                onValueChange = { viewModel.onCarModelChange(it) },
                label = "Modelo do carro",
                placeholder = "Ex: Chevrolet Onix 1.0"
            )

            Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))

            // Seletor de combustível
            Text(
                text = "TIPO DE COMBUSTÍVEL",
                style = RottaTheme.typography.label,
                color = RottaTheme.colors.onSurfaceMuted
            )
            Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RottaTheme.spacing.xs)
            ) {
                FuelType.values().forEach { fuel ->
                    val isSelected = state.fuelType == fuel
                    val bgColor = if (isSelected) RottaTheme.colors.primary else RottaTheme.colors.surfaceVariant
                    val textColor = if (isSelected) RottaTheme.colors.onPrimary else RottaTheme.colors.onSurface

                    RottaSecondaryButton(
                        text = fuel.label,
                        onClick = { viewModel.onFuelTypeChange(fuel) },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(RottaTheme.spacing.sm)
            ) {
                RottaTextField(
                    value = state.consumptionInput,
                    onValueChange = { viewModel.onConsumptionChange(it) },
                    label = "Consumo médio",
                    placeholder = "12.8",
                    suffix = "km/l",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f)
                )
                RottaTextField(
                    value = state.fuelPriceInput,
                    onValueChange = { viewModel.onFuelPriceChange(it) },
                    label = "Preço do litro",
                    placeholder = "5.89",
                    prefix = "R$",
                    suffix = "litro",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

        // 4. BLOCO: CUSTOS FIXOS (Seguro, IPVA, Manutenção, Parcela)
        Text(
            text = "CUSTOS FIXOS DO VEÍCULO",
            style = RottaTheme.typography.label,
            color = RottaTheme.colors.onSurfaceMuted
        )
        Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
        RottaCard {
            RottaTextField(
                value = state.fixedCostsInput,
                onValueChange = { viewModel.onFixedCostsChange(it) },
                label = "Custos fixos mensais (seguro, IPVA, manutenção)",
                placeholder = "920",
                prefix = "R$",
                suffix = "mês",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))

            // Informação secundária escondida atrás de toque
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showFixedDetails = !showFixedDetails },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (showFixedDetails) "Ocultar dias e km diário" else "Ajustar dias e km diário estimado",
                    style = RottaTheme.typography.bodySm,
                    color = RottaTheme.colors.onSurfaceMuted
                )
                Text(
                    text = if (showFixedDetails) "▲" else "▼",
                    style = RottaTheme.typography.bodySm,
                    color = RottaTheme.colors.onSurfaceMuted
                )
            }

            if (showFixedDetails) {
                Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(RottaTheme.spacing.sm)
                ) {
                    RottaTextField(
                        value = state.workDaysInput,
                        onValueChange = { viewModel.onWorkDaysChange(it) },
                        label = "Dias no mês",
                        placeholder = "26",
                        suffix = "dias",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    RottaTextField(
                        value = state.dailyKmInput,
                        onValueChange = { viewModel.onDailyKmChange(it) },
                        label = "Km por dia",
                        placeholder = "160",
                        suffix = "km",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        if (state.isSavedSuccess) {
            Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
            Text(
                text = "Perfil e veículo atualizados com sucesso.",
                style = RottaTheme.typography.bodySm,
                color = RottaTheme.colors.signalGood
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.xl))

        // AÇÃO PRIMÁRIA ÚNICA: Salvar alterações
        RottaPrimaryButton(
            text = "Salvar alterações",
            onClick = { viewModel.saveProfile() }
        )

        // Política de Privacidade & LGPD
        RottaSecondaryButton(
            text = "Política de Privacidade & LGPD",
            onClick = onNavigateToPrivacy,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )

        Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))

        // AÇÃO SECUNDÁRIA: Sair da conta
        RottaSecondaryButton(
            text = "Encerrar sessão",
            onClick = {
                viewModel.logout()
                onLogout()
            },
            modifier = Modifier.height(48.dp)
        )

        Spacer(modifier = Modifier.height(RottaTheme.spacing.xl))
    }
}
