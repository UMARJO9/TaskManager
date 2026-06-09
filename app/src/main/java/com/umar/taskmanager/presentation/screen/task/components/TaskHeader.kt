package com.umar.taskmanager.presentation.screen.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.ui.components.TmColors
import com.umar.taskmanager.ui.components.TmPanel

@Composable
fun TaskHeader(
    task: Task,
    onStatusChange: (TaskStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    TmPanel(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineSmall,
                color = TmColors.Ink
            )

            task.description?.takeIf { it.isNotBlank() }?.let {
                Text(text = it, style = MaterialTheme.typography.bodyLarge, color = TmColors.InkMuted)
            }

            task.deadline?.let {
                Text(
                    text = stringResource(R.string.task_deadline_prefix, it.format(taskDateTimeFormatter)),
                    style = MaterialTheme.typography.labelMedium,
                    color = TmColors.InkMuted
                )
            }

            TaskStatusSelector(
                selected = task.status,
                onStatusChange = onStatusChange
            )
        }
    }
}
