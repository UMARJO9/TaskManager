package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow

class ObserveCurrentUserIdUseCase(
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(): Flow<Long?> = sessionRepository.observeUserId()
}