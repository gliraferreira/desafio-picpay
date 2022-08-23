package com.picpay.desafio.android.users.data.datasource

import com.picpay.desafio.android.users.domain.model.User

interface UsersLocalDataSource {

    fun saveUsers(users: List<User>)

    fun getUsers(): List<User>
}