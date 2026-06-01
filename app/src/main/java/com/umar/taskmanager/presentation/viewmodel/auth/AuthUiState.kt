package com.umar.taskmanager.presentation.viewmodel.auth

import androidx.annotation.StringRes

data class AuthUiState(
    val login: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    @StringRes val errorRes: Int? = null,
    val isLoggedIn: Boolean = false
)
