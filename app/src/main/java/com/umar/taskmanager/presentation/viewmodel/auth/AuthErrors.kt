package com.umar.taskmanager.presentation.viewmodel.auth

import androidx.annotation.StringRes
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.AuthError

@StringRes
fun Throwable.toAuthErrorRes(): Int = when (this) {
    is AuthError.EmptyCredentials -> R.string.error_empty_credentials
    is AuthError.PasswordTooShort -> R.string.error_password_too_short
    is AuthError.LoginTaken -> R.string.error_login_taken
    is AuthError.UserNotFound -> R.string.error_user_not_found
    is AuthError.WrongPassword -> R.string.error_wrong_password
    else -> R.string.error_generic
}
