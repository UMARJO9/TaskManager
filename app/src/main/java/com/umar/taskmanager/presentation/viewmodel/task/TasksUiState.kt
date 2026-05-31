package com.umar.taskmanager.presentation.viewmodel.task

import com.umar.taskmanager.domain.model.Task


data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = true,
    val isLoggedOut: Boolean = false
)