package com.umar.taskmanager.presentation.viewmodel.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus


data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = true,
    val isLoggedOut: Boolean = false,
    val searchQuery: String = "",
    val statusFilter: TaskStatus? = null,
    val hasAnyTasks: Boolean = false
)