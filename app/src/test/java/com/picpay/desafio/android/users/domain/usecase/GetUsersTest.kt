package com.picpay.desafio.android.users.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.core.Result
import com.picpay.desafio.android.testcore.factory.UserFactory.makeUser
import com.picpay.desafio.android.users.domain.model.User
import com.picpay.desafio.android.users.domain.model.UserError
import com.picpay.desafio.android.users.domain.repository.UsersRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class GetUsersTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val usersRepository: UsersRepository = mockk()
    private val getUsers = GetUsers(
        usersRepository = usersRepository
    )

    @Test
    fun invoke_withSuccessResult_returnSuccess() = runTest {
        val users = listOf(
            makeUser(),
            makeUser(),
            makeUser()
        )
        prepareScenario(
            usersResult = Result.Success(users)
        )

        val result = getUsers()

        assertTrue(result is Result.Success)
        assertEquals(users, result.value)
    }

    @Test
    fun invoke_withErrorResult_returnError() = runTest {
        prepareScenario(
            usersResult = Result.Error(UserError.GenericError)
        )

        val result = getUsers()

        assertTrue(result is Result.Error)
        assertEquals(UserError.GenericError, result.value)
    }

    private fun prepareScenario(
        usersResult: Result<List<User>, UserError> = Result.Success(listOf())
    ) {
        coEvery { usersRepository.getUsers() } returns usersResult
    }
}