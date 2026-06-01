package com.umar.taskmanager.domain.usecase.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.repository.TaskRepository
import com.umar.taskmanager.domain.scheduler.TaskReminderScheduler
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var reminderScheduler: TaskReminderScheduler
    private lateinit var useCase: DeleteTaskUseCase

    @Before
    fun setUp() {
        repository = mockk()
        reminderScheduler = mockk(relaxed = true)
        useCase = DeleteTaskUseCase(repository, reminderScheduler)
    }

    @Test
    fun `invoke delegates delete to repository and cancels reminder`() = runTest {
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
        verify(exactly = 1) { reminderScheduler.cancel(task.id) }
    }
}
