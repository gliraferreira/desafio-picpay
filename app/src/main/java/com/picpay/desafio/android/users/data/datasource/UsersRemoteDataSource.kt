package com.picpay.desafio.android.users.data.datasource

import com.picpay.desafio.android.core.Result
import com.picpay.desafio.android.users.domain.model.UserError
import com.picpay.desafio.android.users.domain.model.User

interface UsersRemoteDataSource {

    suspend fun getUsers(): Result<List<User>, UserError>
}
