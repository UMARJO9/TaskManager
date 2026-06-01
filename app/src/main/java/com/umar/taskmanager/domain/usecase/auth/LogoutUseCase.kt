package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.domain.repository.SessionRepository

class LogoutUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() = sessionRepository.clear()
}