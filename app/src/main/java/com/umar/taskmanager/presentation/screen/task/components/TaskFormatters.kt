package com.umar.taskmanager.presentation.screen.task.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.TaskStatus
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
