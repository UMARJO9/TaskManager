package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.data.session.SessionManager
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest {

    private lateinit var sessionManager: SessionManager
    private lateinit var useCase: LogoutUseCase

    @Before
    fun setUp() {
        sessionManager = mockk()
        useCase = LogoutUseCase(sessionManager)
    }

    @Test
    fun `invoke clears the session`() = runTest {
        coEvery { sessionManager.clear() } just Runs

        useCase()

        coVerify(exactly = 1) { sessionManager.clear() }
    }
}
