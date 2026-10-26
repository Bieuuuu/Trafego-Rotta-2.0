package com.traffigorotta.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.traffigorotta.core.design.component.RottaPrimaryButton
import com.traffigorotta.core.design.component.RottaSecondaryButton
import com.traffigorotta.core.design.component.RottaTextField
import com.traffigorotta.core.design.theme.RottaTheme

@Composable
fun LoginScreen(
    onLoginSuccess: (email: String) -> Unit,
    onSkipToCatalog: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RottaTheme.colors.surface)
            .verticalScroll(scrollState)
            .padding(horizontal = RottaTheme.spacing.gutter, vertical = RottaTheme.spacing.xl),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Monograma minimalista "R"
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RottaTheme.shapes.full)
                    .background(RottaTheme.colors.inverseSurface),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "R",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = RottaTheme.colors.onInverseSurface
                )
            }

            Spacer(modifier = Modifier.height(RottaTheme.spacing.lg))

            Text(
                text = "TRAFEGO ROTTA",
                style = RottaTheme.typography.label,
                color = RottaTheme.colors.onSurfaceMuted
            )
            Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
            Text(
                text = "Entrar no copiloto",
                style = RottaTheme.typography.headline,
                color = RottaTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(RottaTheme.spacing.xs))
            Text(
                text = "Seus ganhos e custos sob controle durante a corrida.",
                style = RottaTheme.typography.bodySm,
                color = RottaTheme.colors.onSurfaceMuted
            )

            Spacer(modifier = Modifier.height(RottaTheme.spacing.xl))

            // Campo de e-mail
            RottaTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = null
                },
                label = "E-mail ou telefone",
                placeholder = "motorista@exemplo.com",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(RottaTheme.spacing.md))

            // Campo de senha
            RottaTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null
                },
                label = "Senha ou PIN",
                placeholder = "••••••••",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))
                Text(
                    text = errorMessage!!,
                    style = RottaTheme.typography.bodySm,
                    color = RottaTheme.colors.signalBad
                )
            }
        }

        Column(modifier = Modifier.padding(top = RottaTheme.spacing.xl)) {
            // Ação primária única
            RottaPrimaryButton(
                text = "Entrar",
                onClick = {
                    if (email.isBlank() || !email.contains("@")) {
                        errorMessage = "Informe um e-mail válido para acessar."
                    } else {
                        onLoginSuccess(email)
                    }
                }
            )

            Spacer(modifier = Modifier.height(RottaTheme.spacing.sm))

            // Ação secundária para catálogo de inspeção
            RottaSecondaryButton(
                text = "Ver catálogo de componentes",
                onClick = onSkipToCatalog,
                modifier = Modifier.height(48.dp)
            )
        }
    }
}
