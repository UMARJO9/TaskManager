package com.umar.taskmanager.presentation.screen.task

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.umar.taskmanager.presentation.viewmodel.task.TaskDetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TaskDetailScreen(
    taskId: Long,
    viewModel: TaskDetailViewModel = koinViewModel { parametersOf(taskId) },
    onEdit: () -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler(onBack = onBack)

    LifecycleResumeEffect(Unit) {
        viewModel.refresh()
        onPauseOrDispose { }
    }

    TaskDetailContent(
        state = state,
        onCommentChange = viewModel::onCommentChange,
        onSendComment = viewModel::addComment,
        onStatusChange = viewModel::onStatusChange,
        onEdit = onEdit,
        onBack = onBack
    )
}
