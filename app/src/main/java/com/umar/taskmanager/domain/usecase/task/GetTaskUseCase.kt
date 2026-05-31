package com.umar.taskmanager.domain.usecase.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.repository.TaskRepository

class GetTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Long): Task? {
        return repository.getTask(taskId)
    }
}