package com.umar.taskmanager.domain.model

sealed class AuthError : Exception() {
    data object EmptyCredentials : AuthError()
    data object PasswordTooShort : AuthError()
    data object LoginTaken : AuthError()
    data object UserNotFound : AuthError()
    data object WrongPassword : AuthError()
}
