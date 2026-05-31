package com.umar.taskmanager.domain.usecase.comment

import com.umar.taskmanager.domain.model.Comment
import com.umar.taskmanager.domain.repository.CommentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class AddCommentUseCaseTest {

    private lateinit var repository: CommentRepository
    private lateinit var useCase: AddCommentUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = AddCommentUseCase(repository)
    }

    @Test
    fun `invoke adds a valid comment and returns its id`() = runTest {
        val comment = Comment(
            taskId = 1,
            text = "выглядит хорошо",
            createdAt = LocalDateTime.of(2026, 5, 31, 12, 0)
        )
        coEvery { repository.addComment(comment) } returns 55L

        val id = useCase(comment)

        assertEquals(55L, id)
        coVerify(exactly = 1) { repository.addComment(comment) }
    }

    @Test
    fun `invoke rejects a blank comment without touching repository`() = runTest {
        val comment = Comment(
            taskId = 1,
            text = "   ",
            createdAt = LocalDateTime.of(2026, 5, 31, 12, 0)
        )

        val error = runCatching { useCase(comment) }.exceptionOrNull()

        assertTrue(error is IllegalArgumentException)
        coVerify(exactly = 0) { repository.addComment(any()) }
    }
}
