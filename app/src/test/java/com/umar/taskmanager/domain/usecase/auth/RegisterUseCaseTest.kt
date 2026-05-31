package com.umar.taskmanager.domain.usecase.auth

import com.umar.taskmanager.data.session.SessionManager
import com.umar.taskmanager.domain.model.User
import com.umar.taskmanager.domain.repository.AuthRepository
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
    private lateinit var sessionManager: SessionManager
    private lateinit var useCase: RegisterUseCase

    @Before
    fun setUp() {
        repository = mockk()
        sessionManager = mockk()
        useCase = RegisterUseCase(repository, sessionManager)
    }

    @Test
    fun `successful register saves user id to session and returns user`() = runTest {
        val user = User(id = 7, login = "newuser")
        coEvery { repository.register("newuser", "pass") } returns Result.success(user)
        coEvery { sessionManager.saveUserId(7) } just Runs

        val result = useCase("newuser", "pass")

        assertEquals(user, result.getOrNull())
        coVerify(exactly = 1) { sessionManager.saveUserId(7) }
    }

    @Test
    fun `failed register does not touch session and propagates error`() = runTest {
        val error = IllegalStateException("логин уже занят")
        coEvery { repository.register("newuser", "pass") } returns Result.failure(error)

        val result = useCase("newuser", "pass")

        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
        coVerify(exactly = 0) { sessionManager.saveUserId(any()) }
    }
}
