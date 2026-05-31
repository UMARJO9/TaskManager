package com.umar.taskmanager.presentation.screen.task.components

import com.umar.taskmanager.domain.model.TaskStatus
import java.time.format.DateTimeFormatter

val taskDateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

val taskDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yyyy")

fun TaskStatus.label(): String = when (this) {
    TaskStatus.NEW -> "Новая"
    TaskStatus.IN_PROGRESS -> "В работе"
    TaskStatus.DONE -> "Готово"
}
