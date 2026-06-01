package com.umar.taskmanager.domain.usecase.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTaskUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetTaskUseCase(repository)
    }

    @Test
    fun `invoke returns the task from repository`() = runTest {
        val task = Task(
            id = 3,
            userId = 1,
            title = "Задача",
            description = "описание",
            deadline = null,
            status = TaskStatus.IN_PROGRESS
        )
        coEvery { repository.getTask(3) } returns task

        assertEquals(task, useCase(3))
    }

    @Test
    fun `invoke returns null when task is missing`() = runTest {
        coEvery { repository.getTask(404) } returns null

        assertNull(useCase(404))
    }
}
