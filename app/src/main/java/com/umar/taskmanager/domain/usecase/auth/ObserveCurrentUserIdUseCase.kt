package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.data.session.SessionManager
import kotlinx.coroutines.flow.Flow

class ObserveCurrentUserIdUseCase(
    private val sessionManager: SessionManager
) {
    operator fun invoke(): Flow<Long?> = sessionManager.observeUserId()
}