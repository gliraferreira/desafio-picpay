package com.picpay.desafio.android.users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.users.domain.model.User
import com.picpay.desafio.android.users.domain.model.UserError
import com.picpay.desafio.android.users.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsers: GetUsersUseCase,
    val viewState: UsersViewState
) : ViewModel() {

    fun init() {
        viewState.state.postValue(UsersViewState.State.LOADING)

        viewModelScope.launch {
            getUsers()
                .onSuccess { users ->
                    viewState.state.postValue(UsersViewState.State.SUCCESS)
                    viewState.users.postValue(users)
                }
                .onError { error ->
                    when (error) {
                        UserError.GenericError -> setErrorState()
                        is UserError.Fallback -> setFallbackState(error.fallbackUsers)
                    }
                }
        }
    }

    private fun setFallbackState(fallbackUsers: List<User>) = with(viewState) {
        state.postValue(UsersViewState.State.SUCCESS)
        users.postValue(fallbackUsers)
        action.postValue(UsersViewState.Action.ShowFallbackMessage)
    }

    private fun setErrorState() = with(viewState) {
        state.postValue(UsersViewState.State.ERROR)
        action.postValue(UsersViewState.Action.ShowErrorMessage)
    }
}
