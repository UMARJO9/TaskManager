package com.umar.taskmanager.domain.repository

import com.umar.taskmanager.domain.model.User

interface AuthRepository {
    suspend fun register(login: String, password: String): User
    suspend fun login(login: String, password: String): User
}