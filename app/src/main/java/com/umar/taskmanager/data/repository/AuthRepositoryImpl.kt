package com.umar.taskmanager.data.repository

import com.umar.taskmanager.data.local.dao.UserDao
import com.umar.taskmanager.data.local.entity.UserEntity
import com.umar.taskmanager.data.mapper.toDomain
import com.umar.taskmanager.domain.model.User
import com.umar.taskmanager.domain.repository.AuthRepository
import org.mindrot.jbcrypt.BCrypt

class AuthRepositoryImpl(
    private val userDao: UserDao,
) : AuthRepository {

    override suspend fun register(
        login: String,
        password: String
    ): Result<User> {
        val existing = userDao.getByLogin(login)
        if (existing != null) {
            return Result.failure(Exception("Логин уже занят !"))
        }
        val hash = BCrypt.hashpw(password, BCrypt.gensalt())
        val entity = UserEntity(login = login, passwordHash = hash)
        val id = userDao.insert(entity)
        return Result.success(User(id = id, login = login))
    }

    override suspend fun login(
        login: String,
        password: String
    ): Result<User> {

        val entity =
            userDao.getByLogin(login) ?: return Result.failure(Exception("Пользователь не найден !"))

        val passwordOk = BCrypt.checkpw(password, entity.passwordHash)
        if (!passwordOk) return Result.failure(Exception("Неверный пароль !"))
        return Result.success(entity.toDomain())
    }
}