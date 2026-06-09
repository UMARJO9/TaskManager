package com.umar.taskmanager.presentation.screen.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.ui.components.TmChip
import com.umar.taskmanager.ui.components.TmColors
import com.umar.taskmanager.ui.components.TmIconButton
import com.umar.taskmanager.ui.components.TmTextField

@Composable
fun TasksFilterBar(
    searchQuery: String,
    statusFilter: TaskStatus?,
    onSearchChange: (String) -> Unit,
    onStatusFilterChange: (TaskStatus?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TmTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = stringResource(R.string.tasks_search_placeholder),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = TmColors.InkMuted
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    TmIconButton(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.action_clear),
                        onClick = { onSearchChange("") },
                        modifier = Modifier,
                        tint = TmColors.InkMuted
                    )
                }
            }
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                TmChip(
                    selected = statusFilter == null,
                    onClick = { onStatusFilterChange(null) },
                    text = stringResource(R.string.filter_all)
                )
            }
            items(TaskStatus.entries) { status ->
                TmChip(
                    selected = statusFilter == status,
                    onClick = {
                        onStatusFilterChange(if (statusFilter == status) null else status)
                    },
                    text = status.label()
                )
            }
        }
    }
}
