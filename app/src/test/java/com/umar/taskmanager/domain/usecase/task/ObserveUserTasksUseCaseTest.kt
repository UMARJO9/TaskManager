package com.umar.taskmanager.domain.usecase.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ObserveUserTasksUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: ObserveUserTasksUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = ObserveUserTasksUseCase(repository)
    }

    @Test
    fun `invoke emits the task lists for the given user`() = runTest {
        val tasks = listOf(
            Task(
                id = 1,
                userId = 10,
                title = "Задача",
                description = null,
                deadline = null,
                status = TaskStatus.NEW
            )
        )
        every { repository.observeTasks(10) } returns flowOf(emptyList(), tasks)

        val emissions = useCase(10).toList()

        assertEquals(listOf(emptyList<Task>(), tasks), emissions)
    }
}
