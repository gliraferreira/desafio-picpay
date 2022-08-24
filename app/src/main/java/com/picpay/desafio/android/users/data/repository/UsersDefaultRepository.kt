package com.picpay.desafio.android.users.data.repository

import com.picpay.desafio.android.core.Result
import com.picpay.desafio.android.users.data.datasource.UsersLocalDataSource
import com.picpay.desafio.android.users.data.datasource.UsersRemoteDataSource
import com.picpay.desafio.android.users.domain.model.UserError
import com.picpay.desafio.android.users.domain.model.User
import javax.inject.Inject

class UsersDefaultRepository @Inject constructor(
    private val remoteDataSource: UsersRemoteDataSource,
    private val localDataSource: UsersLocalDataSource
) : UsersRepository {

    override suspend fun getUsers() = when (val remoteResult = remoteDataSource.getUsers()) {
        is Result.Success -> remoteResult.also {
            localDataSource.saveUsers(it.value)
        }
        else -> handleErrorFromRemote(remoteResult)
    }

    private suspend fun handleErrorFromRemote(
        remoteResult: Result<List<User>, UserError>
    ): Result<List<User>, UserError> {
        val localUsers = localDataSource.getUsers()

        return if (localUsers.isNotEmpty()) {
            Result.Error(UserError.Fallback(localUsers))
        } else {
            remoteResult
        }
    }
}
