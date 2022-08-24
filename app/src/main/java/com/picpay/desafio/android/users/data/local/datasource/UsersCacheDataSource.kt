package com.picpay.desafio.android.users.data.local.datasource

import com.picpay.desafio.android.users.data.local.database.UsersDao
import com.picpay.desafio.android.users.data.local.mapper.UserEntityToModelMapper
import com.picpay.desafio.android.users.data.local.mapper.UserModelToEntityMapper
import com.picpay.desafio.android.users.domain.model.User
import javax.inject.Inject

class UsersCacheDataSource @Inject constructor(
    private val usersDao: UsersDao,
    private val userModelToEntityMapper: UserModelToEntityMapper,
    private val userEntityToModelMapper: UserEntityToModelMapper
) : UsersLocalDataSource {

    override suspend fun saveUsers(users: List<User>) {
        users
            .map(userModelToEntityMapper::mapFrom)
            .let { usersDao.insertAll(it) }
    }

    override suspend fun getUsers(): List<User> {
        return usersDao.getAll().map(userEntityToModelMapper::mapFrom)
    }
}