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

class RegisterUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var sessionRepository: SessionRepository
    private lateinit var useCase: RegisterUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sessionRepository = mockk()
        useCase = RegisterUseCase(repository, sessionRepository)
    }

    @Test
    fun `successful register saves user id to session and returns user`() = runTest {
        val user = User(id = 7, login = "newuser")
        coEvery { repository.register("newuser", "secret") } returns user
        coEvery { sessionRepository.saveUserId(7) } just Runs

        val result = useCase("newuser", "secret")

        assertEquals(user, result)
        coVerify(exactly = 1) { sessionRepository.saveUserId(7) }
    }

    @Test
    fun `failed register does not touch session and propagates error`() = runTest {
        coEvery { repository.register("newuser", "secret") } throws AuthError.LoginTaken

        val error = runCatching { useCase("newuser", "secret") }.exceptionOrNull()

        assertTrue(error is AuthError.LoginTaken)
        coVerify(exactly = 0) { sessionRepository.saveUserId(any()) }
    }

    @Test
    fun `short password is rejected before hitting the repository`() = runTest {
        val error = runCatching { useCase("newuser", "123") }.exceptionOrNull()

        assertTrue(error is AuthError.PasswordTooShort)
        coVerify(exactly = 0) { repository.register(any(), any()) }
        coVerify(exactly = 0) { sessionRepository.saveUserId(any()) }
    }

    @Test
    fun `blank credentials are rejected before hitting the repository`() = runTest {
        val error = runCatching { useCase("", "") }.exceptionOrNull()

        assertTrue(error is AuthError.EmptyCredentials)
        coVerify(exactly = 0) { repository.register(any(), any()) }
    }
}
