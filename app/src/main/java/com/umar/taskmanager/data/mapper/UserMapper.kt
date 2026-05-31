package com.umar.taskmanager.data.mapper

import com.umar.taskmanager.data.local.entity.UserEntity
import com.umar.taskmanager.domain.model.User

fun UserEntity.toDomain(): User = User(
    id = id,
    login = login
)
