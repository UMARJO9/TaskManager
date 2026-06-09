package com.umar.taskmanager.data.mapper

import com.umar.taskmanager.data.local.entity.TaskEntity
import com.umar.taskmanager.domain.model.Task

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    userId = userId,
    title = title,
    description = description,
    deadline = deadline,
    status = status,
    isFavorite = isFavorite
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = id,
    userId = userId,
    title = title,
    description = description,
    deadline = deadline,
    status = status,
    isFavorite = isFavorite
)
