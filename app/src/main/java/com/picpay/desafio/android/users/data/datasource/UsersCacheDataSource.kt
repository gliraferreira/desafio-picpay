package com.picpay.desafio.android.users.data.datasource

import com.picpay.desafio.android.users.data.database.UsersDao
import com.picpay.desafio.android.users.data.mapper.UserEntityToModelMapper
import com.picpay.desafio.android.users.data.mapper.UserModelToEntityMapper
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
            .let { userEntities ->
                usersDao.insertAll(userEntities)
            }
    }

    override suspend fun getUsers(): List<User> {
        return usersDao.getAll().map(userEntityToModelMapper::mapFrom)
    }
}