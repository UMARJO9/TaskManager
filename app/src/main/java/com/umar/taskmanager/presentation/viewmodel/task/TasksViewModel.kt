package com.umar.taskmanager.presentation.viewmodel.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umar.taskmanager.domain.usecase.auth.LogoutUseCase
import com.umar.taskmanager.domain.usecase.auth.ObserveCurrentUserIdUseCase
import com.umar.taskmanager.domain.usecase.task.DeleteTaskUseCase
import com.umar.taskmanager.domain.usecase.task.ObserveUserTasksUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import com.umar.taskmanager.domain.model.Task
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TasksViewModel(
    observeCurrentUserIdUseCase: ObserveCurrentUserIdUseCase,
    private val observeUserTasksUseCase: ObserveUserTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TasksUiState())
    val state: StateFlow<TasksUiState> = _state.asStateFlow()

    init {
        observeCurrentUserIdUseCase()
            .flatMapLatest { userId ->
                if (userId == null) {
                    flowOf(emptyList<Task>())
                } else {
                    observeUserTasksUseCase(userId)
                }
            }.onEach { tasks->
                _state.update { it.copy(tasks = tasks, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    fun onDeleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            logoutUseCase()
            _state.update { it.copy(isLoggedOut = true) }
        }
    }

}