package com.umar.taskmanager.data.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.usecase.task.GetTaskUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TaskReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val getTaskUseCase: GetTaskUseCase by inject()

    override suspend fun doWork(): Result {
        val taskId = inputData.getLong(KEY_TASK_ID, -1L)
        if (taskId <= 0L) return Result.success()

        val task = getTaskUseCase(taskId) ?: return Result.success()
        if (task.status == TaskStatus.DONE) return Result.success()

        NotificationHelper.showTaskReminder(applicationContext, task)
        return Result.success()
    }

    companion object {
        const val KEY_TASK_ID = "task_id"
    }
}