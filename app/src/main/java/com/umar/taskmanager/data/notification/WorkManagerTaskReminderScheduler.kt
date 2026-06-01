package com.umar.taskmanager.data.notification

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.scheduler.TaskReminderScheduler
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class WorkManagerTaskReminderScheduler(
    private val context: Context
) : TaskReminderScheduler {

    override fun schedule(task: Task) {
        val deadline = task.deadline
        if (deadline == null || task.status == TaskStatus.DONE) {
            cancel(task.id)
            return
        }

        val deadlineMillis = deadline.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val now = System.currentTimeMillis()
        if (deadlineMillis <= now) {
            cancel(task.id)
            return
        }

        val reminderMillis = deadlineMillis - TimeUnit.MINUTES.toMillis(REMINDER_LEAD_MINUTES)
        val delay = (reminderMillis - now).coerceAtLeast(0L)

        val request = OneTimeWorkRequestBuilder<TaskReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(workDataOf(TaskReminderWorker.KEY_TASK_ID to task.id))
            .addTag(TAG)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            workName(task.id),
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    override fun cancel(taskId: Long) {
        WorkManager.getInstance(context).cancelUniqueWork(workName(taskId))
    }

    companion object {
        const val REMINDER_LEAD_MINUTES = 60L
        private const val TAG = "task_reminder"
        private fun workName(taskId: Long) = "task_reminder_$taskId"
    }
}