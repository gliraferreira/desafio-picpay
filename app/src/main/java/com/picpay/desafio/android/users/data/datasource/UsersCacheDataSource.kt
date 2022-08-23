package com.picpay.desafio.android.users.data.datasource

import com.picpay.desafio.android.users.domain.model.User
import javax.inject.Inject

class UsersCacheDataSource @Inject constructor() : UsersLocalDataSource {

    override fun saveUsers(users: List<User>) {
    }

    override fun getUsers(): List<User> {
        return emptyList()
    }
}