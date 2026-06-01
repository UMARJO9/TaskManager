package com.umar.taskmanager.presentation.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        _state.update { it.copy(login = value, errorRes = null) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(password = value, errorRes = null) }
    }

    fun login() {
        val current = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorRes = null) }
            runCatching {
                loginUseCase(current.login, current.password)
            }.onSuccess {
                _state.update { it.copy(isLoading = false, isLoggedIn = true) }
            }.onFailure { e ->
                _state.update { it.copy(isLoading = false, errorRes = e.toAuthErrorRes()) }
            }
        }
    }

    fun register() {
        val current = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorRes = null) }
            runCatching {
                registerUseCase(current.login, current.password)
            }.onSuccess {
                _state.update { it.copy(isLoading = false, isLoggedIn = true) }
            }.onFailure { e ->
                _state.update { it.copy(isLoading = false, errorRes = e.toAuthErrorRes()) }
            }
        }
    }
}