package com.umar.taskmanager.domain.usecase.task

import com.umar.taskmanager.domain.model.Task
import com.umar.taskmanager.domain.model.TaskStatus
import com.umar.taskmanager.domain.repository.TaskRepository
import com.umar.taskmanager.domain.scheduler.TaskReminderScheduler
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AddTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var reminderScheduler: TaskReminderScheduler
    private lateinit var useCase: AddTaskUseCase

    @Before
    fun setUp() {
        repository = mockk()
        reminderScheduler = mockk(relaxed = true)
        useCase = AddTaskUseCase(repository, reminderScheduler)
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

    @Test
    fun `invoke schedules reminder for saved task with new id`() = runTest {
        val task = Task(
            userId = 1,
            title = "Задача",
            description = null,
            deadline = null,
            status = TaskStatus.NEW
        )
        coEvery { repository.addTask(task) } returns 99L

        useCase(task)

        verify(exactly = 1) { reminderScheduler.schedule(task.copy(id = 99L)) }
    }
}
