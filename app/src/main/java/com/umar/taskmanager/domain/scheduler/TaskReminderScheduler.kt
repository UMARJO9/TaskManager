package com.umar.taskmanager.domain.scheduler

import com.umar.taskmanager.domain.model.Task

interface TaskReminderScheduler {
    fun schedule(task: Task)
    fun cancel(taskId: Long)
}