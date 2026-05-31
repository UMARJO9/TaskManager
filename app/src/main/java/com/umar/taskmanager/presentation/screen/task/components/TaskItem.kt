package com.umar.taskmanager.presentation.screen.task.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.domain.model.Task

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                task.description?.takeIf { it.isNotBlank() }?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(
                        onClick = onClick,
                        label = { Text(task.status.label()) }
                    )
                    task.deadline?.let {
                        Text(
                            text = it.format(taskDateTimeFormatter),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить")
            }
        }
    }
}