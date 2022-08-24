package com.picpay.desafio.android.users.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.core.Result
import com.picpay.desafio.android.testcore.CoroutinesTestRule
import com.picpay.desafio.android.testcore.factory.UserFactory.makeUser
import com.picpay.desafio.android.users.domain.model.User
import com.picpay.desafio.android.users.domain.model.UserError
import com.picpay.desafio.android.users.domain.usecase.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    @get:Rule
    val rule = CoroutinesTestRule()

    @get:Rule
    val instantTask = InstantTaskExecutorRule()

    private val getUsers: GetUsersUseCase = mockk()
    private val viewState = UsersViewState()
    private val viewModel by lazy {
        UsersViewModel(
            getUsers = getUsers,
            viewState = viewState
        )
    }

    @Test
    fun init_withSuccessUsersResult_setStateToSuccess() {
        val users = listOf(
            makeUser(),
            makeUser(),
            makeUser()
        )
        prepareScenario(
            usersResult = Result.Success(users)
        )

        viewModel.init()

        assertEquals(UsersViewState.State.SUCCESS, viewState.state.value)
    }

    @Test
    fun init_withSuccessUsersResult_setUsersOnViewState() {
        val users = listOf(
            makeUser(),
            makeUser(),
            makeUser()
        )
        prepareScenario(
            usersResult = Result.Success(users)
        )

        viewModel.init()

        assertEquals(users, viewState.users.value)
    }

    @Test
    fun init_withGenericErrorResult_setErrorState() {
        prepareScenario(
            usersResult = Result.Error(UserError.GenericError)
        )

        viewModel.init()

        assertEquals(UsersViewState.State.ERROR, viewState.state.value)
    }

    @Test
    fun init_withGenericErrorResult_sendErrorAction() {
        prepareScenario(
            usersResult = Result.Error(UserError.GenericError)
        )

        viewModel.init()

        assertEquals(UsersViewState.Action.ShowErrorMessage, viewState.action.value)
    }

    @Test
    fun init_withFallbackResult_setSuccessState() {
        val users = listOf(
            makeUser(),
            makeUser(),
            makeUser()
        )
        val fallbackError = UserError.Fallback(
            fallbackUsers = users
        )
        prepareScenario(
            usersResult = Result.Error(fallbackError)
        )

        viewModel.init()

        assertEquals(UsersViewState.State.SUCCESS, viewState.state.value)
    }

    @Test
    fun init_withFallbackResult_setFallbackUsersOnViewState() {
        val users = listOf(
            makeUser(),
            makeUser(),
            makeUser()
        )
        val fallbackError = UserError.Fallback(
            fallbackUsers = users
        )
        prepareScenario(
            usersResult = Result.Error(fallbackError)
        )

        viewModel.init()

        assertEquals(users, viewState.users.value)
    }

    private fun prepareScenario(
        usersResult: Result<List<User>, UserError> = Result.Success(listOf())
    ) {
        coEvery { getUsers() } returns usersResult
        viewState.users.value = null
    }
}
