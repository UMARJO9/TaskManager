package com.umar.taskmanager.domain.usecase.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.repository.TaskRepository
import com.umar.taskmanager.domain.scheduler.TaskReminderScheduler

class AddTaskUseCase(
    private val repository: TaskRepository,
    private val reminderScheduler: TaskReminderScheduler
) {
    suspend operator fun invoke(task: Task): Long {
        val id = repository.addTask(task)
        reminderScheduler.schedule(task.copy(id = id))
        return id
    }
}
