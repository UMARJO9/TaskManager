package com.umar.taskmanager.presentation.viewmodel.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.usecase.auth.LogoutUseCase
import com.umar.taskmanager.domain.usecase.auth.ObserveCurrentUserIdUseCase
import com.umar.taskmanager.domain.usecase.task.DeleteTaskUseCase
import com.umar.taskmanager.domain.usecase.task.ObserveUserTasksUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TasksViewModel(
    observeCurrentUserIdUseCase: ObserveCurrentUserIdUseCase,
    private val observeUserTasksUseCase: ObserveUserTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val tasksFlow = observeCurrentUserIdUseCase()
        .flatMapLatest { userId ->
            if (userId == null) flowOf(emptyList()) else observeUserTasksUseCase(userId)
        }

    private val searchQuery = MutableStateFlow("")
    private val statusFilter = MutableStateFlow<TaskStatus?>(null)
    private val loggedOut = MutableStateFlow(false)

    val state: StateFlow<TasksUiState> = combine(
        tasksFlow,
        searchQuery,
        statusFilter,
        loggedOut
    ) { tasks, query, filter, isLoggedOut ->
        TasksUiState(
            tasks = applyFilters(tasks, query, filter),
            isLoading = false,
            isLoggedOut = isLoggedOut,
            searchQuery = query,
            statusFilter = filter,
            hasAnyTasks = tasks.isNotEmpty()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TasksUiState()
    )

    private fun applyFilters(
        tasks: List<Task>,
        query: String,
        filter: TaskStatus?
    ): List<Task> {
        val trimmed = query.trim()
        return tasks.filter { task ->
            val matchesStatus = filter == null || task.status == filter
            val matchesQuery = trimmed.isBlank() ||
                task.title.contains(trimmed, ignoreCase = true) ||
                task.description?.contains(trimmed, ignoreCase = true) == true
            matchesStatus && matchesQuery
        }
    }

    fun onSearchChange(query: String) {
        searchQuery.value = query
    }

    fun onStatusFilterChange(filter: TaskStatus?) {
        statusFilter.value = filter
    }

    fun onDeleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            logoutUseCase()
            loggedOut.update { true }
        }
    }
}