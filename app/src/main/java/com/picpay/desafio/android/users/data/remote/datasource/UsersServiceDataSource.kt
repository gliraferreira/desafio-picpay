package com.picpay.desafio.android.users.data.remote.datasource

import com.picpay.desafio.android.core.Result
import com.picpay.desafio.android.users.data.remote.api.UsersApi
import com.picpay.desafio.android.users.data.remote.mapper.UserResponseToModelMapper
import com.picpay.desafio.android.users.domain.model.UserError
import javax.inject.Inject

class UsersServiceDataSource @Inject constructor(
    private val usersApi: UsersApi,
    private val userResponseMapper: UserResponseToModelMapper
) : UsersRemoteDataSource {

    override suspend fun getUsers() = try {
        val usersResult = usersApi.getUsers().map(userResponseMapper::mapFrom)
        Result.Success(usersResult)
    } catch (exception: Exception) {
        Result.Error(UserError.GenericError)
    }
}
