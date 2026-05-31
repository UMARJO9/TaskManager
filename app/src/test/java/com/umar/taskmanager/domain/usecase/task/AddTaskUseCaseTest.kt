package com.umar.taskmanager.domain.usecase.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AddTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: AddTaskUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = AddTaskUseCase(repository)
    }

    @Test
    fun `invoke delegates to repository and returns new task id`() = runTest {
        val task = Task(
            userId = 1,
            title = "Задача",
            description = null,
            deadline = null,
            status = TaskStatus.NEW
        )
        coEvery { repository.addTask(task) } returns 99L

        val id = useCase(task)

        assertEquals(99L, id)
        coVerify(exactly = 1) { repository.addTask(task) }
    }
}
