package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.domain.model.AuthError
import com.umar.taskmanager.domain.model.User
import com.umar.taskmanager.domain.repository.AuthRepository
import com.umar.taskmanager.domain.repository.SessionRepository

class LoginUseCase(
    private val repository: AuthRepository,
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(login: String, password: String): User {
        if (login.isBlank() || password.isBlank()) throw AuthError.EmptyCredentials
        val user = repository.login(login.trim(), password)
        sessionRepository.saveUserId(user.id)
        return user
    }
}