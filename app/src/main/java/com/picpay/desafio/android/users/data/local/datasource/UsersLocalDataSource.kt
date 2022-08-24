package com.picpay.desafio.android.users.data.local.datasource

import com.picpay.desafio.android.users.domain.model.User

interface UsersLocalDataSource {

    suspend fun saveUsers(users: List<User>)

    suspend fun getUsers(): List<User>
}