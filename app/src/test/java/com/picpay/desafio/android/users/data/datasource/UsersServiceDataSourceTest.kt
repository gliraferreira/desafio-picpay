package com.picpay.desafio.android.users.data.datasource

import com.picpay.desafio.android.core.Result
import com.picpay.desafio.android.testcore.MainDispatcherRule
import com.picpay.desafio.android.users.data.api.UsersApi
import com.picpay.desafio.android.users.data.api.response.UserResponse
import com.picpay.desafio.android.users.data.mapper.UserResponseToModelMapper
import com.picpay.desafio.android.users.domain.model.UserError
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class UsersServiceDataSourceTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val usersApi: UsersApi = mockk()
    private val userResponseMapper: UserResponseToModelMapper = mockk()
    private val dataSource = UsersServiceDataSource(
        usersApi = usersApi,
        userResponseMapper = userResponseMapper
    )

    @Test
    fun getUsers_withSuccessResult_returnSuccess() = runTest {
        val response = listOf<UserResponse>(
            mockk(),
            mockk(),
            mockk()
        )
        prepareScenario(
            isSuccessScenario = true,
            usersResponse = response
        )

        val result = dataSource.getUsers()

        assertTrue(result is Result.Success)
    }

    @Test
    fun getUsers_withSuccessResult_returnSameListFromResponse() = runTest {
        val response = listOf<UserResponse>(
            mockk(),
            mockk(),
            mockk()
        )
        prepareScenario(
            isSuccessScenario = true,
            usersResponse = response
        )

        val result = dataSource.getUsers()

        assertTrue(result is Result.Success)
        assertEquals(response.size, result.value.size)
    }

    @Test
    fun getUsers_withException_returnGenericError() = runTest {
        prepareScenario(
            isSuccessScenario = false,
            exception = Exception()
        )

        val result = dataSource.getUsers()

        assertTrue(result is Result.Error)
        assertEquals(UserError.GenericError, result.value)
    }

    private fun prepareScenario(
        isSuccessScenario: Boolean = false,
        usersResponse: List<UserResponse> = emptyList(),
        exception: Exception = Exception()
    ) {
        every { userResponseMapper.mapFrom(any()) } returns mockk()

        if (isSuccessScenario) {
            coEvery { usersApi.getUsers() } returns usersResponse
        } else {
            coEvery { usersApi.getUsers() } throws exception
        }
    }
}
