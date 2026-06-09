package com.umar.taskmanager.presentation.screen.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.presentation.screen.task.components.DeadlinePickerDialog
import com.umar.taskmanager.presentation.screen.task.components.TaskStatusSelector
import com.umar.taskmanager.presentation.screen.task.components.taskDateFormatter
import com.umar.taskmanager.presentation.viewmodel.task.AddTaskUiState
import com.umar.taskmanager.ui.components.TmButton
import com.umar.taskmanager.ui.components.TmButtonVariant
import com.umar.taskmanager.ui.components.TmColors
import com.umar.taskmanager.ui.components.TmIconButton
import com.umar.taskmanager.ui.components.TmScreen
import com.umar.taskmanager.ui.components.TmTextField
import com.umar.taskmanager.ui.components.TmTopBar
import java.time.LocalDateTime

@Composable
fun AddTaskContent(
    state: AddTaskUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDeadlineChange: (LocalDateTime?) -> Unit,
    onStatusChange: (TaskStatus) -> Unit,
    onClear: () -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = TmColors.Background,
        topBar = {
            TmTopBar(
                title = stringResource(
                    if (state.isEditMode) R.string.add_task_edit_title
                    else R.string.add_task_new_title
                ),
                navigationIcon = {
                    TmIconButton(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.action_back),
                        onClick = onBack
                    )
                }
            )
        }
    ) { innerPadding ->
        TmScreen(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TmTextField(
                    value = state.title,
                    onValueChange = onTitleChange,
                    label = stringResource(R.string.task_title_label),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isSaving
                )

                TmTextField(
                    value = state.description,
                    onValueChange = onDescriptionChange,
                    label = stringResource(R.string.task_description_label),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    enabled = !state.isSaving
                )

                TaskStatusSelector(
                    selected = state.status,
                    onStatusChange = onStatusChange,
                    enabled = !state.isSaving
                )

                Text(
                    text = stringResource(R.string.task_deadline_label),
                    style = MaterialTheme.typography.labelLarge,
                    color = TmColors.Ink
                )
                TmButton(
                    text = state.deadline?.format(taskDateFormatter)
                        ?: stringResource(R.string.task_pick_date),
                    onClick = { showDatePicker = true },
                    enabled = !state.isSaving,
                    variant = TmButtonVariant.Secondary
                )

                state.errorRes?.let {
                    Text(text = stringResource(it), color = MaterialTheme.colorScheme.error)
                }

                TmButton(
                    text = stringResource(
                        if (state.isEditMode) R.string.action_update
                        else R.string.action_save
                    ),
                    onClick = onSave,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isSaving,
                    isLoading = state.isSaving
                )

                val hasInput = state.title.isNotEmpty() ||
                    state.description.isNotEmpty() ||
                    state.deadline != null ||
                    state.status != TaskStatus.NEW
                if (hasInput) {
                    TmButton(
                        text = stringResource(R.string.action_clear),
                        onClick = onClear,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isSaving,
                        variant = TmButtonVariant.Secondary
                    )
                }
            }
        }
    }

    if (showDatePicker) {
        DeadlinePickerDialog(
            onDateSelected = onDeadlineChange,
            onDismiss = { showDatePicker = false }
        )
    }
}
