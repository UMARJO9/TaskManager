package com.umar.taskmanager.domain.model

import java.time.LocalDateTime

data class Task(
    val id: Long = 0,
    val userId: Long,
    val title: String,
    val description: String?,
    val deadline: LocalDateTime?,
    val status: TaskStatus
)
