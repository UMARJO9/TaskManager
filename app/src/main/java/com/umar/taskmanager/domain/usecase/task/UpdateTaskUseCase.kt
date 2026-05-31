package com.umar.taskmanager.domain.usecase.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.repository.TaskRepository

class UpdateTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        repository.updateTask(task)
    }
}