package com.umar.taskmanager.presentation.viewmodel.auth

data class AuthUiState(
    val login: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false
)
