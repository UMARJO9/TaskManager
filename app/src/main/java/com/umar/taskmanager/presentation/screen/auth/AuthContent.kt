package com.umar.taskmanager.presentation.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.presentation.viewmodel.auth.AuthUiState
import com.umar.taskmanager.ui.components.TmButton
import com.umar.taskmanager.ui.components.TmButtonVariant
import com.umar.taskmanager.ui.components.TmColors
import com.umar.taskmanager.ui.components.TmLoading
import com.umar.taskmanager.ui.components.TmPanel
import com.umar.taskmanager.ui.components.TmScreen
import com.umar.taskmanager.ui.components.TmTextField

@Composable
fun AuthContent(
    state: AuthUiState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    TmScreen(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            TmPanel(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    Text(
                        text = stringResource(R.string.auth_title),
                        style = MaterialTheme.typography.headlineMedium,
                        color = TmColors.Ink
                    )

                    TmTextField(
                        value = state.login,
                        onValueChange = onLoginChange,
                        label = stringResource(R.string.auth_login),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isLoading,
                        singleLine = true
                    )

                    TmTextField(
                        value = state.password,
                        onValueChange = onPasswordChange,
                        label = stringResource(R.string.auth_password),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isLoading,
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation()
                    )

                    state.errorRes?.let {
                        Text(
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    TmButton(
                        text = stringResource(R.string.auth_sign_in),
                        onClick = onLoginClick,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isLoading,
                        isLoading = state.isLoading
                    )

                    TmButton(
                        text = stringResource(R.string.auth_register),
                        onClick = onRegisterClick,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isLoading,
                        variant = TmButtonVariant.Secondary
                    )
                }
            }

            if (state.isLoading) {
                TmLoading(modifier = Modifier.align(Alignment.TopCenter))
            }
        }
    }
}
