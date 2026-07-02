package com.umar.taskmanager.presentation.screen.task.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.Priority
import com.umar.taskmanager.ui.components.TmColors
import com.umar.taskmanager.ui.components.TmShapes

@Composable
fun PrioritySelector(
    selected: Priority,
    onPriorityChange: (Priority) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.task_priority_label),
            style = MaterialTheme.typography.labelLarge,
            color = TmColors.Ink
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Priority.entries.forEach { priority ->
                PriorityChip(
                    priority = priority,
                    selected = selected == priority,
                    enabled = enabled,
                    onClick = { onPriorityChange(priority) }
                )
            }
        }
    }
}

@Composable
private fun PriorityChip(
    priority: Priority,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val color = priority.color()
    Surface(
        modifier = Modifier.clickable(enabled = enabled, onClick = onClick),
        shape = TmShapes.Pill,
        color = if (selected) color else TmColors.Surface,
        border = BorderStroke(1.dp, if (selected) color else TmColors.Line)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!selected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(color, TmShapes.Pill)
                )
            }
            Text(
                text = priority.label(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (selected) TmColors.OnPrimary else TmColors.InkMuted
            )
        }
    }
}
