package com.umar.taskmanager.data.mapper

import com.umar.taskmanager.data.local.entity.CommentEntity
import com.umar.taskmanager.domain.model.Comment

fun CommentEntity.toDomain(): Comment = Comment(
    id = id,
    taskId = taskId,
    text = text,
    createdAt = createdAt
)

fun Comment.toEntity(): CommentEntity = CommentEntity(
    id = id,
    taskId = taskId,
    text = text,
    createdAt = createdAt
)