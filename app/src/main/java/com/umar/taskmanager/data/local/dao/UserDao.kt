package com.umar.taskmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umar.taskmanager.data.local.entity.UserEntity


@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    suspend fun getByLogin(login: String): UserEntity?
}