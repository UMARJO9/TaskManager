package com.umar.taskmanager.domain.repository

import com.umar.taskmanager.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(login: String, password: String): Result<User>
    suspend fun login(login: String, password: String): Result<User>
    suspend fun logout()
    fun observeCurrentUserId(): Flow<Long?>
}