package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.data.session.SessionManager
import com.umar.taskmanager.domain.model.User
import com.umar.taskmanager.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(login: String, password: String): Result<User> {
        val result = repository.register(login, password)
        result.getOrNull()?.let { user ->
            sessionManager.saveUserId(user.id)
        }
        return result
    }
}