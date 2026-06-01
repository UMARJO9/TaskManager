package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.data.session.SessionManager
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ObserveCurrentUserIdUseCaseTest {

    private lateinit var sessionManager: SessionManager
    private lateinit var useCase: ObserveCurrentUserIdUseCase

    @Before
    fun setUp() {
        sessionManager = mockk()
        useCase = ObserveCurrentUserIdUseCase(sessionManager)
    }

    @Test
    fun `invoke emits the user ids from the session`() = runTest {
        every { sessionManager.observeUserId() } returns flowOf(null, 5L)

        val emissions = useCase().toList()

        assertEquals(listOf(null, 5L), emissions)
    }
}
