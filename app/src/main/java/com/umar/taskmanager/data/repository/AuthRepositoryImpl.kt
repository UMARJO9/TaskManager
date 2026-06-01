package com.umar.taskmanager.data.repository

import com.umar.taskmanager.data.local.dao.UserDao
import com.umar.taskmanager.data.local.entity.UserEntity
import com.umar.taskmanager.data.mapper.toDomain
import com.umar.taskmanager.domain.model.AuthError
import com.umar.taskmanager.domain.model.User
import com.umar.taskmanager.domain.repository.AuthRepository
import org.mindrot.jbcrypt.BCrypt

class AuthRepositoryImpl(
    private val userDao: UserDao,
) : AuthRepository {

    override suspend fun register(login: String, password: String): User {
        if (userDao.getByLogin(login) != null) throw AuthError.LoginTaken

        val hash = BCrypt.hashpw(password, BCrypt.gensalt())
        val entity = UserEntity(login = login, passwordHash = hash)
        val id = userDao.insert(entity)
        return User(id = id, login = login)
    }

    override suspend fun login(login: String, password: String): User {
        val entity = userDao.getByLogin(login) ?: throw AuthError.UserNotFound

        if (!BCrypt.checkpw(password, entity.passwordHash)) throw AuthError.WrongPassword
        return entity.toDomain()
    }
}