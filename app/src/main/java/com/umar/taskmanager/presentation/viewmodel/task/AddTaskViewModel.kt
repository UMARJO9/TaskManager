package com.umar.taskmanager.presentation.viewmodel.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.Priority
import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.usecase.auth.ObserveCurrentUserIdUseCase
import com.umar.taskmanager.domain.usecase.task.AddTaskUseCase
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

class AddTaskViewModel(
    private val taskId: Long,
    observeCurrentUserIdUseCase: ObserveCurrentUserIdUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    private val isEditMode: Boolean = taskId != 0L

    private val _state = MutableStateFlow(AddTaskUiState(isEditMode = isEditMode))
    val state: StateFlow<AddTaskUiState> = _state.asStateFlow()

    private var currentUserId: Long? = null
    private var currentIsFavorite: Boolean = false

    init {
        observeCurrentUserIdUseCase()
            .onEach { currentUserId = it }
            .launchIn(viewModelScope)

        if (isEditMode) {
            viewModelScope.launch {
                getTaskUseCase(taskId)?.let { task ->
                    _state.update {
                        it.copy(
                            title = task.title,
                            description = task.description.orEmpty(),
                            deadline = task.deadline,
                            status = task.status,
                            priority = task.priority
                        )
                    }
                    currentIsFavorite = task.isFavorite
                }
            }
        }
    }

    fun onTitleChange(value: String) {
        _state.update { it.copy(title = value, errorRes = null) }
    }

    fun onDescriptionChange(value: String) {
        _state.update { it.copy(description = value, errorRes = null) }
    }

    fun onDeadlineChange(value: LocalDateTime?) {
        _state.update { it.copy(deadline = value) }
    }

    fun onStatusChange(value: TaskStatus) {
        _state.update { it.copy(status = value) }
    }

    fun onPriorityChange(value: Priority) {
        _state.update { it.copy(priority = value) }
    }

    fun clearForm() {
        _state.update {
            it.copy(
                title = "",
                description = "",
                deadline = null,
                status = TaskStatus.NEW,
                priority = Priority.MEDIUM,
                errorRes = null
            )
        }
    }

    fun save() {
        val current = _state.value
        if (current.title.isBlank()) {
            _state.update { it.copy(errorRes = R.string.error_task_title_required) }
            return
        }
        val userId = currentUserId
        if (userId == null) {
            _state.update { it.copy(errorRes = R.string.error_session_missing) }
            return
        }

        val task = Task(
            id = taskId,
            userId = userId,
            title = current.title.trim(),
            description = current.description.trim().ifBlank { null },
            deadline = current.deadline,
            status = current.status,
            priority = current.priority,
            isFavorite = currentIsFavorite
        )

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, errorRes = null) }
            runCatching {
                if (isEditMode) updateTaskUseCase(task) else addTaskUseCase(task)
            }.onSuccess {
                _state.update { it.copy(isSaving = false, isSaved = true) }
            }.onFailure {
                _state.update { it.copy(isSaving = false, errorRes = R.string.error_save_task) }
            }
        }
    }
}
