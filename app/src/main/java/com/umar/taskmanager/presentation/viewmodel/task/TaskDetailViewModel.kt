package com.umar.taskmanager.presentation.viewmodel.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                .onFailure { e ->
                    _state.update {
                        it.copy(task = task, error = e.message ?: "Не удалось обновить статус")
                    }
                }
        }
    }

    fun onCommentChange(value: String) {
        _state.update { it.copy(commentInput = value, error = null) }
    }

    fun addComment() {
        val text = _state.value.commentInput.trim()
        if (text.isBlank()) {
            _state.update { it.copy(error = "Комментарий не должен быть пустым") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSending = true, error = null) }
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
            }.onFailure { e ->
                _state.update {
                    it.copy(isSending = false, error = e.message ?: "Ошибка отправки")
                }
            }
        }
    }
}