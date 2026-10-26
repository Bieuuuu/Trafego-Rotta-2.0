package com.traffigorotta.ui.disclosure

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffigorotta.core.design.component.RottaButton
import com.traffigorotta.core.design.component.RottaButtonVariant
import com.traffigorotta.core.design.component.RottaCard
import com.traffigorotta.core.design.theme.RottaTheme

/**
 * Tela de Consentimento Destacado (Prominent Disclosure)
 * Requisito inegociável da Google Play Store e do AGENTS.md.
 *
 * Explica com total transparência o uso do AccessibilityService (somente leitura)
 * e sobreposição (Overlay), garantindo que nada é clicado nem dados pessoais armazenados.
 */
@Composable
fun ProminentDisclosureScreen(
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RottaTheme.colors
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Monograma sóbrio de cabeçalho
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(colors.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "R",
                color = colors.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Transparência e Permissões",
            style = RottaTheme.typography.headline,
            color = colors.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "O Rotta é um copiloto de relance para quando você está ao volante. Veja como suas permissões são tratadas com segurança total:",
            style = RottaTheme.typography.body,
            color = colors.onSurfaceMuted
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Bloco 1: Acessibilidade (Somente Leitura)
        RottaCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "1. ACESSIBILIDADE • SOMENTE LEITURA",
                style = RottaTheme.typography.label,
                color = colors.onSurfaceMuted
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "O que fazemos:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = colors.onSurface
            )
            Text(
                text = "Lemos apenas o valor oferecido (R$), a distância estimada (km) e o tempo estimado (min) das corridas exibidas pelo Uber e 99.",
                style = RottaTheme.typography.bodySm,
                color = colors.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "O que NUNCA fazemos:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = colors.onSurface
            )
            Text(
                text = "• Nunca clicamos, aceitamos ou recusamos corridas.\n• A decisão é 100% sua.\n• Não lemos nem guardamos nome, foto, nota ou endereço exato de passageiros.",
                style = RottaTheme.typography.bodySm,
                color = colors.onSurfaceMuted
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bloco 2: Sobreposição (Overlay)
        RottaCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "2. SOBREPOSIÇÃO DE TELA (OVERLAY)",
                style = RottaTheme.typography.label,
                color = colors.onSurfaceMuted
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Bolha discreta e arrastável:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = colors.onSurface
            )
            Text(
                text = "Exibe uma bolha compacta que mostra o ganho acumulado da jornada atual e o semáforo (Vale, Neutra ou Fraca) sem cobrir os botões de ação do app de corrida.",
                style = RottaTheme.typography.bodySm,
                color = colors.onSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bloco 3: Direito de Recusa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(colors.surfaceVariant)
                .border(1.dp, colors.outline, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Você pode recusar agora e continuar usando o Rotta normalmente para iniciar jornadas, lançar valores manuais, acompanhar metas e ver relatórios.",
                style = RottaTheme.typography.bodySm,
                color = colors.onSurfaceMuted
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Ações primária e secundária na área do polegar
        RottaButton(
            text = "Concordar e Continuar",
            onClick = onAccept,
            variant = RottaButtonVariant.PRIMARY,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        RottaButton(
            text = "Agora não (Usar sem automação)",
            onClick = onDecline,
            variant = RottaButtonVariant.SECONDARY,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}
