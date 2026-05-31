package com.umar.taskmanager.presentation.viewmodel.task

import com.umar.taskmanager.domain.model.TaskStatus
import java.time.LocalDateTime

data class AddTaskUiState(
    val title: String = "",
    val description: String = "",
    val deadline: LocalDateTime? = null,
    val status: TaskStatus = TaskStatus.NEW,
    val isSaving: Boolean = false,
    val error: String? = null,
    val isSaved: Boolean = false,
    val isEditMode: Boolean = false
)