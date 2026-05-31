package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.data.session.SessionManager

class LogoutUseCase(
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke() = sessionManager.clear()
}