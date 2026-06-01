package com.umar.taskmanager.domain.usecase.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.repository.TaskRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: DeleteTaskUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = DeleteTaskUseCase(repository)
    }

    @Test
    fun `invoke delegates delete to repository`() = runTest {
        val task = Task(
            id = 8,
            userId = 1,
            title = "Задача",
            description = null,
            deadline = null,
            status = TaskStatus.NEW
        )
        coEvery { repository.deleteTask(task) } just Runs

        useCase(task)

        coVerify(exactly = 1) { repository.deleteTask(task) }
    }
}
