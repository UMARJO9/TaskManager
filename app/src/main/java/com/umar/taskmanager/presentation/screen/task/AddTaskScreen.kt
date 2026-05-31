package com.umar.taskmanager.presentation.screen.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umar.taskmanager.presentation.viewmodel.task.AddTaskViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AddTaskScreen(
    taskId: Long = 0L,
    viewModel: AddTaskViewModel = koinViewModel { parametersOf(taskId) },
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onSaved()
    }

    AddTaskContent(
        state = state,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onDeadlineChange = viewModel::onDeadlineChange,
        onStatusChange = viewModel::onStatusChange,
        onClear = viewModel::clearForm,
        onSave = viewModel::save,
        onBack = onBack
    )
}