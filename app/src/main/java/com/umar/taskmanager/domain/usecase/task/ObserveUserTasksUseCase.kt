package com.umar.taskmanager.domain.usecase.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class ObserveUserTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(userId: Long): Flow<List<Task>> {
        return repository.observeTasks(userId)
    }
}