package com.traffigorotta.ui.privacy

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.traffigorotta.core.design.component.RottaCard
import com.traffigorotta.core.design.component.RottaSecondaryButton
import com.traffigorotta.core.design.theme.RottaTheme

/**
 * Tela nativa da Política de Privacidade & LGPD do Trafego Rotta.
 * Apresenta as garantias de privacidade, ausência de cliques autônomos,
 * minimização de dados e armazenamento 100% local.
 */
@Composable
fun PrivacyPolicyScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RottaTheme.colors
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
            .padding(horizontal = RottaTheme.spacing.gutter, vertical = RottaTheme.spacing.lg)
            .verticalScroll(scrollState)
    ) {
        // Cabeçalho
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "TRANSPARÊNCIA • LGPD",
                    style = RottaTheme.typography.label,
                    color = colors.onSurfaceMuted
                )
                Text(
                    text = "Política de Privacidade",
                    style = RottaTheme.typography.headline,
                    color = colors.onSurface
                )
            }

            RottaSecondaryButton(
                text = "Voltar",
                onClick = onNavigateBack,
                modifier = Modifier.height(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

        // 1. Número Dominante: 100% no Aparelho
        RottaCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "100% LOCAL",
                style = RottaTheme.typography.display,
                color = colors.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Seus ganhos e rotina pertencem a você. Nenhum dado é vendido ou enviado para servidores de anúncios.",
                style = RottaTheme.typography.bodySm,
                color = colors.onSurfaceMuted
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

        // 2. Acessibilidade Restrita
        RottaCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "ACESSIBILIDADE: SOMENTE LEITURA",
                style = RottaTheme.typography.label,
                color = colors.onSurfaceMuted
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "• O Rotta lê apenas os números das tarifas (R$, km e minutos).\n" +
                       "• Zero ações automáticas: o app NUNCA clica, toca, aceita ou cancela corridas.\n" +
                       "• A decisão é sempre sua.",
                style = RottaTheme.typography.bodySm,
                color = colors.onSurface
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

        // 3. Minimização de Dados (LGPD)
        RottaCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "DADOS DE PASSAGEIROS",
                style = RottaTheme.typography.label,
                color = colors.onSurfaceMuted
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "O Rotta descarta ativamente qualquer informação identificável de passageiro. Nomes, fotos, notas ou endereços exatos nunca são processados nem guardados.",
                style = RottaTheme.typography.bodySm,
                color = colors.onSurface
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

        // 4. Seus Direitos (LGPD Art. 18)
        RottaCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "SEUS DIREITOS",
                style = RottaTheme.typography.label,
                color = colors.onSurfaceMuted
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "• Exportação integral em CSV a qualquer momento na aba Relatórios.\n" +
                       "• Exclusão imediata ao limpar dados do app ou desinstalar.\n" +
                       "• Revogação de permissões a qualquer instante nas configurações do Android.",
                style = RottaTheme.typography.bodySm,
                color = colors.onSurface
            )
        }

        Spacer(modifier = Modifier.height(RottaTheme.spacing.xl))

        RottaSecondaryButton(
            text = "Fechar",
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )

        Spacer(modifier = Modifier.height(RottaTheme.spacing.xl))
    }
}
