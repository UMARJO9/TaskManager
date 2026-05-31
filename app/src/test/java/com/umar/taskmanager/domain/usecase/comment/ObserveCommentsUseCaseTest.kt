package com.umar.taskmanager.domain.usecase.comment

import com.umar.taskmanager.domain.model.Comment
import com.umar.taskmanager.domain.repository.CommentRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class ObserveCommentsUseCaseTest {

    private lateinit var repository: CommentRepository
    private lateinit var useCase: ObserveCommentsUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = ObserveCommentsUseCase(repository)
    }

    @Test
    fun `invoke emits the comments for the given task`() = runTest {
        val comments = listOf(
            Comment(
                id = 1,
                taskId = 7,
                text = "первый",
                createdAt = LocalDateTime.of(2026, 5, 31, 9, 0)
            )
        )
        every { repository.observeComments(7) } returns flowOf(emptyList(), comments)

        val emissions = useCase(7).toList()

        assertEquals(listOf(emptyList<Comment>(), comments), emissions)
    }
}
