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

class UpdateTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var reminderScheduler: TaskReminderScheduler
    private lateinit var useCase: UpdateTaskUseCase

    @Before
    fun setUp() {
        repository = mockk()
        reminderScheduler = mockk(relaxed = true)
        useCase = UpdateTaskUseCase(repository, reminderScheduler)
    }

    @Test
    fun `invoke delegates update to repository and reschedules reminder`() = runTest {
        val task = Task(
            id = 5,
            userId = 1,
            title = "Обновлено",
            description = null,
            deadline = null,
            status = TaskStatus.DONE
        )
        coEvery { repository.updateTask(task) } just Runs

        useCase(task)

        coVerify(exactly = 1) { repository.updateTask(task) }
        verify(exactly = 1) { reminderScheduler.schedule(task) }
    }
}
