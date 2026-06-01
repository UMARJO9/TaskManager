package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.domain.repository.SessionRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ObserveCurrentUserIdUseCaseTest {

    private lateinit var sessionRepository: SessionRepository
    private lateinit var useCase: ObserveCurrentUserIdUseCase

    @Before
    fun setUp() {
        sessionRepository = mockk()
        useCase = ObserveCurrentUserIdUseCase(sessionRepository)
    }

    @Test
    fun `invoke emits the user ids from the session`() = runTest {
        every { sessionRepository.observeUserId() } returns flowOf(null, 5L)

        val emissions = useCase().toList()

        assertEquals(listOf(null, 5L), emissions)
    }
}
