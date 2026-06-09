package com.umar.taskmanager.presentation.screen.task.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.umar.taskmanager.R
import com.umar.taskmanager.ui.components.TmButton
import com.umar.taskmanager.ui.components.TmButtonVariant
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlinePickerDialog(
    onDateSelected: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit
) {
    val todayUtcMillis = remember {
        LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                utcTimeMillis >= todayUtcMillis

            override fun isSelectableYear(year: Int): Boolean =
                year >= LocalDate.now().year
        }
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TmButton(
                text = stringResource(R.string.action_ok),
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()
                        onDateSelected(date.atStartOfDay())
                    }
                    onDismiss()
                },
                modifier = androidx.compose.ui.Modifier.width(112.dp)
            )
        },
        dismissButton = {
            TmButton(
                text = stringResource(R.string.action_cancel),
                onClick = onDismiss,
                modifier = androidx.compose.ui.Modifier.width(128.dp),
                variant = TmButtonVariant.Secondary
            )
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
