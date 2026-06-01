package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.domain.model.AuthError
import com.umar.taskmanager.domain.model.User
import com.umar.taskmanager.domain.repository.AuthRepository
import com.umar.taskmanager.domain.repository.SessionRepository

class RegisterUseCase(
    private val repository: AuthRepository,
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(login: String, password: String): User {
        if (login.isBlank() || password.isBlank()) throw AuthError.EmptyCredentials
        if (password.length < MIN_PASSWORD_LENGTH) throw AuthError.PasswordTooShort
        val user = repository.register(login.trim(), password)
        sessionRepository.saveUserId(user.id)
        return user
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }
}