package com.umar.taskmanager.presentation.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umar.taskmanager.presentation.viewmodel.auth.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = koinViewModel(),
    onLoggedIn: ()-> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) onLoggedIn()
    }
    AuthContent(
        state = state,
        onLoginChange = viewModel::onLoginChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::login,
        onRegisterClick = viewModel::register
    )
}