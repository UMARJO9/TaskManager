package com.umar.taskmanager.presentation.screen.task.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.ui.components.TmIconButton

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TaskCard(
        task = task,
        onClick = onClick,
        onDelete = onDelete,
        onFavoriteClick = onFavoriteClick,
        modifier = modifier
    )
}

@Composable
fun TaskCard(
    task: Task,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusStyle = task.status.statusCardStyle()

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.small,
        color = statusStyle.container,
        border = BorderStroke(1.dp, statusStyle.border),
        shadowElevation = 1.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(statusStyle.accent)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 14.dp, end = 8.dp, bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = task.title,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = statusStyle.content,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    TaskStatusBadge(
                        status = task.status,
                        style = statusStyle
                    )
                }

                task.description?.takeIf { it.isNotBlank() }?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = statusStyle.secondaryContent,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                task.deadline?.let {
                    Text(
                        text = stringResource(
                            R.string.task_deadline_prefix,
                            it.format(taskDateTimeFormatter)
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = statusStyle.secondaryContent
                    )
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TmIconButton(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(
                        if (task.isFavorite) {
                            R.string.action_remove_from_favorites
                        } else {
                            R.string.action_add_to_favorites
                        }
                    ),
                    onClick = onFavoriteClick,
                    tint = if (task.isFavorite) Color(0xFFFFB300) else statusStyle.mutedIconTint,
                    containerColor = statusStyle.buttonContainer
                )
                TmIconButton(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.action_delete),
                    onClick = onDelete,
                    tint = statusStyle.deleteTint,
                    containerColor = statusStyle.buttonContainer
                )
            }
        }
    }
}

@Composable
private fun TaskStatusBadge(
    status: TaskStatus,
    style: TaskStatusCardStyle,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = style.badgeContainer,
        contentColor = style.badgeContent
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(style.badgeContent, MaterialTheme.shapes.extraSmall)
            )
            Text(
                text = status.label(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }
    }
}

private data class TaskStatusCardStyle(
    val container: Color,
    val border: Color,
    val accent: Color,
    val content: Color,
    val secondaryContent: Color,
    val badgeContainer: Color,
    val badgeContent: Color,
    val mutedIconTint: Color,
    val buttonContainer: Color,
    val deleteTint: Color
)

@Composable
private fun TaskStatus.statusCardStyle(): TaskStatusCardStyle {
    val colorScheme = MaterialTheme.colorScheme

    return when (this) {
        TaskStatus.NEW -> TaskStatusCardStyle(
            container = Color(0xFFFFFBEB),
            border = Color(0xFFF5D58A),
            accent = Color(0xFFF59E0B),
            content = Color(0xFF2F2410),
            secondaryContent = Color(0xFF725B25),
            badgeContainer = Color(0xFFFFE8B5),
            badgeContent = Color(0xFF9A5800),
            mutedIconTint = Color(0xFFB99A52),
            buttonContainer = Color(0xFFFFF3D0),
            deleteTint = Color(0xFF9A5800)
        )

        TaskStatus.IN_PROGRESS -> TaskStatusCardStyle(
            container = Color(0xFFEFF6FF),
            border = Color(0xFFB8D7FF),
            accent = Color(0xFF2563EB),
            content = Color(0xFF10233F),
            secondaryContent = Color(0xFF315177),
            badgeContainer = Color(0xFFDCEBFF),
            badgeContent = Color(0xFF1D4ED8),
            mutedIconTint = Color(0xFF88A7D4),
            buttonContainer = Color(0xFFE1EDFF),
            deleteTint = Color(0xFF1D4ED8)
        )

        TaskStatus.DONE -> TaskStatusCardStyle(
            container = Color(0xFFF0FDF4),
            border = Color(0xFFAADFB8),
            accent = Color(0xFF16A34A),
            content = Color(0xFF102819),
            secondaryContent = Color(0xFF2F6841),
            badgeContainer = Color(0xFFDDF8E5),
            badgeContent = Color(0xFF15803D),
            mutedIconTint = Color(0xFF85B894),
            buttonContainer = Color(0xFFE2F7E8),
            deleteTint = colorScheme.onSurfaceVariant
        )
    }
}
