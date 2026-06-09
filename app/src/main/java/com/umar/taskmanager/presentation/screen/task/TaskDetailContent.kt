package com.umar.taskmanager.presentation.screen.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.presentation.screen.task.components.CommentInputBar
import com.umar.taskmanager.presentation.screen.task.components.CommentItem
import com.umar.taskmanager.presentation.screen.task.components.TaskHeader
import com.umar.taskmanager.presentation.viewmodel.task.TaskDetailUiState
import com.umar.taskmanager.ui.components.TmColors
import com.umar.taskmanager.ui.components.TmIconButton
import com.umar.taskmanager.ui.components.TmLoading
import com.umar.taskmanager.ui.components.TmScreen
import com.umar.taskmanager.ui.components.TmTopBar

@Composable
fun TaskDetailContent(
    state: TaskDetailUiState,
    onCommentChange: (String) -> Unit,
    onSendComment: () -> Unit,
    onStatusChange: (TaskStatus) -> Unit,
    onEdit: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = TmColors.Background,
        topBar = {
            TmTopBar(
                title = stringResource(R.string.task_detail_title),
                navigationIcon = {
                    TmIconButton(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.action_back),
                        onClick = onBack
                    )
                },
                actions = {
                    if (state.task != null) {
                        TmIconButton(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.action_edit),
                            onClick = onEdit
                        )
                    }
                }
            )
        },
        bottomBar = {
            CommentInputBar(
                value = state.commentInput,
                isSending = state.isSending,
                onValueChange = onCommentChange,
                onSend = onSendComment
            )
        }
    ) { innerPadding ->
        TmScreen(modifier = Modifier.padding(innerPadding)) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TmLoading()
                    }
                }

                state.task == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.task_not_found),
                            color = TmColors.InkMuted
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item { TaskHeader(task = state.task, onStatusChange = onStatusChange) }

                        item {
                            Text(
                                text = stringResource(R.string.comments_title, state.comments.size),
                                style = MaterialTheme.typography.titleMedium,
                                color = TmColors.Ink
                            )
                            HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                        }

                        if (state.comments.isEmpty()) {
                            item {
                                Text(
                                    text = stringResource(R.string.comments_empty),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TmColors.InkMuted
                                )
                            }
                        } else {
                            items(items = state.comments, key = { it.id }) { comment ->
                                CommentItem(comment)
                            }
                        }

                        state.errorRes?.let { error ->
                            item {
                                Text(text = stringResource(error), color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}
