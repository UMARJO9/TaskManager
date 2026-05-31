package com.umar.taskmanager.domain.model

import java.time.LocalDateTime

data class Comment(
    val id: Long = 0,
    val taskId: Long,
    val text: String,
    val createdAt: LocalDateTime
)
