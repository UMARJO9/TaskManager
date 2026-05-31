package com.umar.taskmanager.data.repository

import com.umar.taskmanager.data.local.dao.TaskDao
import com.umar.taskmanager.data.mapper.toDomain
import com.umar.taskmanager.data.mapper.toEntity
import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun getTask(id: Long): Task? =
        taskDao.getById(id)?.toDomain()

    override suspend fun addTask(task: Task): Long =
        taskDao.insert(task.toEntity())

    override suspend fun updateTask(task: Task) =
        taskDao.update(task.toEntity())

    override suspend fun deleteTask(task: Task) =
        taskDao.delete(task.toEntity())

    override fun observeTasks(userId: Long): Flow<List<Task>> =
        taskDao.observeTasks(userId).map { list -> list.map { it.toDomain() } }
}