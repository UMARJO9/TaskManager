package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.domain.repository.SessionRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest {

    private lateinit var sessionRepository: SessionRepository
    private lateinit var useCase: LogoutUseCase

    @Before
    fun setUp() {
        sessionRepository = mockk()
        useCase = LogoutUseCase(sessionRepository)
    }

    @Test
    fun `invoke clears the session`() = runTest {
        coEvery { sessionRepository.clear() } just Runs

        useCase()

        coVerify(exactly = 1) { sessionRepository.clear() }
    }
}
