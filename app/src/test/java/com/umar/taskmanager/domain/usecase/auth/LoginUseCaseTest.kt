package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.domain.model.AuthError
import com.umar.taskmanager.domain.model.User
import com.umar.taskmanager.domain.repository.AuthRepository
import com.umar.taskmanager.domain.repository.SessionRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var sessionRepository: SessionRepository
    private lateinit var useCase: LoginUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sessionRepository = mockk()
        useCase = LoginUseCase(repository, sessionRepository)
    }

    @Test
    fun `successful login saves user id to session and returns user`() = runTest {
        val user = User(id = 42, login = "umar")
        coEvery { repository.login("umar", "pass") } returns user
        coEvery { sessionRepository.saveUserId(42) } just Runs

        val result = useCase("umar", "pass")

        assertEquals(user, result)
        coVerify(exactly = 1) { sessionRepository.saveUserId(42) }
    }

    @Test
    fun `failed login does not touch session and propagates error`() = runTest {
        coEvery { repository.login("umar", "bad") } throws AuthError.WrongPassword

        val error = runCatching { useCase("umar", "bad") }.exceptionOrNull()

        assertTrue(error is AuthError.WrongPassword)
        coVerify(exactly = 0) { sessionRepository.saveUserId(any()) }
    }

    @Test
    fun `blank credentials are rejected before hitting the repository`() = runTest {
        val error = runCatching { useCase("", "") }.exceptionOrNull()

        assertTrue(error is AuthError.EmptyCredentials)
        coVerify(exactly = 0) { repository.login(any(), any()) }
        coVerify(exactly = 0) { sessionRepository.saveUserId(any()) }
    }
}
