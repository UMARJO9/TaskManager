package com.umar.taskmanager.presentation.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.presentation.viewmodel.auth.AuthUiState

@Composable
fun AuthContent(
    state: AuthUiState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Авторизация",
                style = MaterialTheme.typography.headlineMedium
            )

            OutlinedTextField(
                value = state.login,
                onValueChange = onLoginChange,
                label = { Text("Логин") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = { Text("Пароль") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
                visualTransformation = PasswordVisualTransformation()
            )

            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Text("Войти")
            }

            OutlinedButton(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Text("Регистрация")
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}