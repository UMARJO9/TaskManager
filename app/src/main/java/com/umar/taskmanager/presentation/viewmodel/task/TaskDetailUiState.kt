package com.umar.taskmanager.presentation.viewmodel.task

import androidx.annotation.StringRes
import com.umar.taskmanager.domain.model.Comment
import com.umar.taskmanager.domain.model.Task

data class TaskDetailUiState(
    val task: Task? = null,
    val comments: List<Comment> = emptyList(),
    val commentInput: String = "",
    val isLoading: Boolean = true,
    val isSending: Boolean = false,
    @StringRes val errorRes: Int? = null
)