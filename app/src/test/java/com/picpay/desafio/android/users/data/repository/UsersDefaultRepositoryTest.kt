package com.picpay.desafio.android.users.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.core.Result
import com.picpay.desafio.android.testcore.factory.UserFactory.makeUser
import com.picpay.desafio.android.users.data.local.datasource.UsersLocalDataSource
import com.picpay.desafio.android.users.data.remote.datasource.UsersRemoteDataSource
import com.picpay.desafio.android.users.domain.model.User
import com.picpay.desafio.android.users.domain.model.UserError
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class UsersDefaultRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val remoteDataSource: UsersRemoteDataSource = mockk()
    private val localDataSource: UsersLocalDataSource = mockk(relaxed = true)
    private val repository = UsersDefaultRepository(
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource
    )

    @Test
    fun getUsers_withRemoteSuccess_returnRemoteUsers() = runTest {
        val remoteUsers = listOf(
            makeUser(),
            makeUser(),
            makeUser()
        )
        prepareScenario(
            remoteResult = Result.Success(remoteUsers)
        )

        val result = repository.getUsers()

        assertTrue(result is Result.Success)
        assertEquals(remoteUsers, result.value)
    }

    @Test
    fun getUsers_withRemoteSuccess_saveUsersInLocalSource() = runTest {
        val remoteUsers = listOf(
            makeUser(),
            makeUser(),
            makeUser()
        )
        prepareScenario(
            remoteResult = Result.Success(remoteUsers)
        )

        val result = repository.getUsers()

        coVerify {
            localDataSource.saveUsers(remoteUsers)
        }
    }

    @Test
    fun getUsers_withRemoteErrorAndSavedLocalUsers_returnFallbackUsers() = runTest {
        val localUsers = listOf(
            makeUser(),
            makeUser(),
            makeUser()
        )
        prepareScenario(
            remoteResult = Result.Error(UserError.GenericError),
            localUsers = localUsers
        )

        val result = repository.getUsers()

        assertTrue(result is Result.Error)
        val resultValue = result.value
        assertTrue(resultValue is UserError.Fallback)
        assertEquals(localUsers, resultValue.fallbackUsers)
    }

    @Test
    fun getUsers_withRemoteErrorAndNoLocalUsers_returnError() = runTest {
        prepareScenario(
            remoteResult = Result.Error(UserError.GenericError),
            localUsers = emptyList()
        )

        val result = repository.getUsers()

        assertTrue(result is Result.Error)
        assertEquals(UserError.GenericError, result.value)
    }

    private fun prepareScenario(
        remoteResult: Result<List<User>, UserError> = Result.Success(listOf()),
        localUsers: List<User> = listOf()
    ) {
        coEvery { remoteDataSource.getUsers() } returns remoteResult
        coEvery { localDataSource.getUsers() } returns localUsers
    }
}
