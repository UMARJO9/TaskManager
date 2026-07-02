package com.umar.taskmanager.presentation.screen.task.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.Priority
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.ui.components.TmColors
import java.time.format.DateTimeFormatter

val taskDateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

val taskDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("dd.MM.yyyy")

@StringRes
fun TaskStatus.labelRes(): Int = when (this) {
    TaskStatus.NEW -> R.string.status_new
    TaskStatus.IN_PROGRESS -> R.string.status_in_progress
    TaskStatus.DONE -> R.string.status_done
}

@Composable
fun TaskStatus.label(): String = stringResource(labelRes())

@StringRes
fun Priority.labelRes(): Int = when (this) {
    Priority.HIGH -> R.string.priority_high
    Priority.MEDIUM -> R.string.priority_medium
    Priority.LOW -> R.string.priority_low
}

@Composable
fun Priority.label(): String = stringResource(labelRes())

fun Priority.color(): Color = when (this) {
    Priority.HIGH -> TmColors.PriorityHigh
    Priority.MEDIUM -> TmColors.PriorityMedium
    Priority.LOW -> TmColors.PriorityLow
}
