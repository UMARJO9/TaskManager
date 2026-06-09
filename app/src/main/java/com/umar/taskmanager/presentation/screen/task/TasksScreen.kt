package com.umar.taskmanager.presentation.screen.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.presentation.viewmodel.task.TasksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TasksScreen(
    viewModel: TasksViewModel = koinViewModel(),
    onAddTask: () -> Unit,
    onOpenTask: (Task) -> Unit,
    onLoggedOut: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) onLoggedOut()
    }

    TasksContent(
        state = state,
        onAddTask = onAddTask,
        onOpenTask = onOpenTask,
        onDeleteTask = viewModel::onDeleteTask,
        onFavoriteClick = viewModel::onFavoriteClick,
        onSearchChange = viewModel::onSearchChange,
        onStatusFilterChange = viewModel::onStatusFilterChange,
        onLogout = viewModel::onLogout
    )
}
