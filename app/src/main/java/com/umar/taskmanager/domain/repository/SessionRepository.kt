package com.umar.taskmanager.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun observeUserId(): Flow<Long?>
    suspend fun saveUserId(userId: Long)
    suspend fun clear()
}
