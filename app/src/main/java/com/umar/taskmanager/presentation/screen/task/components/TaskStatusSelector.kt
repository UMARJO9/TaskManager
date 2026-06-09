package com.umar.taskmanager.presentation.screen.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.ui.components.TmChip
import com.umar.taskmanager.ui.components.TmColors

@Composable
fun TaskStatusSelector(
    selected: TaskStatus,
    onStatusChange: (TaskStatus) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.task_status_label),
            style = MaterialTheme.typography.labelLarge,
            color = TmColors.Ink
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TaskStatus.entries.forEach { status ->
                TmChip(
                    selected = selected == status,
                    onClick = { onStatusChange(status) },
                    text = status.label(),
                    enabled = enabled
                )
            }
        }
    }
}
