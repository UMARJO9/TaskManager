package com.umar.taskmanager.presentation.screen.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.presentation.screen.task.components.TaskItem
import com.umar.taskmanager.presentation.screen.task.components.TasksFilterBar
import com.umar.taskmanager.presentation.viewmodel.task.TasksUiState
import com.umar.taskmanager.ui.components.TmBottomBar
import com.umar.taskmanager.ui.components.TmBottomBarItem
import com.umar.taskmanager.ui.components.TmButton
import com.umar.taskmanager.ui.components.TmColors
import com.umar.taskmanager.ui.components.TmConfirmDialog
import com.umar.taskmanager.ui.components.TmFab
import com.umar.taskmanager.ui.components.TmIconButton
import com.umar.taskmanager.ui.components.TmLoading
import com.umar.taskmanager.ui.components.TmScreen
import com.umar.taskmanager.ui.components.TmTopBar

@Composable
fun TasksContent(
    state: TasksUiState,
    onAddTask: () -> Unit,
    onOpenTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    onFavoriteClick: (Task) -> Unit,
    onSearchChange: (String) -> Unit,
    onStatusFilterChange: (TaskStatus?) -> Unit,
    onLogout: () -> Unit
) {
    var taskPendingDelete by remember { mutableStateOf<Task?>(null) }
    var selectedTab by remember { mutableStateOf(TasksBottomTab.Home) }

    val visibleTasks = when (selectedTab) {
        TasksBottomTab.Home -> state.tasks
        TasksBottomTab.Favorites -> state.tasks.filter { it.isFavorite }
        TasksBottomTab.Profile -> emptyList()
    }

    Scaffold(
        containerColor = TmColors.Background,
        topBar = {
            TmTopBar(
                title = stringResource(R.string.tasks_title),
                actions = {
                    TmIconButton(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = stringResource(R.string.action_logout),
                        onClick = onLogout
                    )
                }
            )
        },
        floatingActionButton = {
            if (selectedTab != TasksBottomTab.Profile) {
                TmFab(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.tasks_add),
                    onClick = onAddTask
                )
            }
        },
        bottomBar = {
            TasksBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        TmScreen(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                when {
                    state.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            TmLoading()
                        }
                    }

                    selectedTab == TasksBottomTab.Profile -> {
                        ProfileTab(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            onLogout = onLogout
                        )
                    }

                    !state.hasAnyTasks -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.tasks_empty),
                                style = MaterialTheme.typography.bodyLarge,
                                color = TmColors.InkMuted,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    else -> {
                        if (selectedTab == TasksBottomTab.Home) {
                            TasksFilterBar(
                                searchQuery = state.searchQuery,
                                statusFilter = state.statusFilter,
                                onSearchChange = onSearchChange,
                                onStatusFilterChange = onStatusFilterChange
                            )
                        }

                        if (visibleTasks.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(
                                        if (selectedTab == TasksBottomTab.Favorites) {
                                            R.string.favorites_empty
                                        } else {
                                            R.string.tasks_nothing_found
                                        }
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TmColors.InkMuted,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(items = visibleTasks, key = { it.id }) { task ->
                                    TaskItem(
                                        task = task,
                                        onClick = { onOpenTask(task) },
                                        onDelete = { taskPendingDelete = task },
                                        onFavoriteClick = { onFavoriteClick(task) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    taskPendingDelete?.let { task ->
        TmConfirmDialog(
            title = stringResource(R.string.task_delete_title),
            message = stringResource(R.string.task_delete_message, task.title),
            confirmText = stringResource(R.string.action_delete),
            dismissText = stringResource(R.string.action_cancel),
            onConfirm = {
                onDeleteTask(task)
                taskPendingDelete = null
            },
            onDismiss = { taskPendingDelete = null }
        )
    }
}

@Composable
private fun TasksBottomBar(
    selectedTab: TasksBottomTab,
    onTabSelected: (TasksBottomTab) -> Unit
) {
    TmBottomBar {
        TasksBottomTab.entries.forEach { tab ->
            TmBottomBarItem(
                selected = selectedTab == tab,
                label = stringResource(tab.labelRes),
                icon = tab.icon,
                onClick = { onTabSelected(tab) },
            )
        }
    }
}

@Composable
private fun ProfileTab(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.profile_title),
                style = MaterialTheme.typography.headlineSmall,
                color = TmColors.Ink
            )
            TmButton(
                text = stringResource(R.string.action_logout),
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private enum class TasksBottomTab(
    val labelRes: Int,
    val icon: ImageVector
) {
    Home(R.string.bottom_home, Icons.Default.Home),
    Favorites(R.string.bottom_favorites, Icons.Default.Star),
    Profile(R.string.bottom_profile, Icons.Default.Person)
}
