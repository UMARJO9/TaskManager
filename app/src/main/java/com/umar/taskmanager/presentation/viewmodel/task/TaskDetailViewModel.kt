package com.umar.taskmanager.presentation.viewmodel.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.Comment
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.usecase.comment.AddCommentUseCase
import com.umar.taskmanager.domain.usecase.comment.ObserveCommentsUseCase
import com.umar.taskmanager.domain.usecase.task.GetTaskUseCase
import com.umar.taskmanager.domain.usecase.task.UpdateTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TaskDetailViewModel(
    private val taskId: Long,
    private val getTaskUseCase: GetTaskUseCase,
    private val observeCommentsUseCase: ObserveCommentsUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TaskDetailUiState())
    val state: StateFlow<TaskDetailUiState> = _state.asStateFlow()

    init {
        refresh()
        observeCommentsUseCase(taskId)
            .onEach { comments ->
                _state.update { it.copy(comments = comments) }
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        viewModelScope.launch {
            val task = getTaskUseCase(taskId)
            _state.update { it.copy(task = task, isLoading = false) }
        }
    }

    fun onStatusChange(status: TaskStatus) {
        val task = _state.value.task ?: return
        if (task.status == status) return
        val updated = task.copy(status = status)
        _state.update { it.copy(task = updated) }
        viewModelScope.launch {
            runCatching { updateTaskUseCase(updated) }
                .onFailure {
                    _state.update {
                        it.copy(task = task, errorRes = R.string.error_status_update)
                    }
                }
        }
    }

    fun onCommentChange(value: String) {
        _state.update { it.copy(commentInput = value, errorRes = null) }
    }

    fun addComment() {
        val text = _state.value.commentInput.trim()
        if (text.isBlank()) {
            _state.update { it.copy(errorRes = R.string.error_comment_empty) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSending = true, errorRes = null) }
            runCatching {
                addCommentUseCase(
                    Comment(
                        taskId = taskId,
                        text = text,
                        createdAt = LocalDateTime.now()
                    )
                )
            }.onSuccess {
                _state.update { it.copy(isSending = false, commentInput = "") }
            }.onFailure {
                _state.update {
                    it.copy(isSending = false, errorRes = R.string.error_comment_send)
                }
            }
        }
    }
}