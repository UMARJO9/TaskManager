package com.umar.taskmanager.presentation.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umar.taskmanager.domain.usecase.auth.LoginUseCase
import com.umar.taskmanager.domain.usecase.auth.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state.asStateFlow()

    fun onLoginChange(value: String) {
        _state.update { it.copy(login = value, error = null) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(password = value, error = null) }
    }

    fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val current = _state.value
            val result = loginUseCase(current.login, current.password)
            result.onSuccess {
                _state.update { it.copy(isLoading = false, isLoggedIn = true) }
            }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }

        }
    }

    fun register() {
        val current = _state.value
        if (current.login.isBlank() || current.password.isBlank()) {
            _state.update { it.copy(error = "Заполните логин и пароль") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val result = registerUseCase(current.login, current.password)
            result.onSuccess {
                _state.update { it.copy(isLoading = false, isLoggedIn = true) }
            }.onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}