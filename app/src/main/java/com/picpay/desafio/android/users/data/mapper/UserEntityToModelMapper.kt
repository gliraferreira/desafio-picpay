package com.picpay.desafio.android.users.data.mapper

import com.picpay.desafio.android.users.data.database.entity.UserEntity
import com.picpay.desafio.android.users.domain.model.User
import javax.inject.Inject

class UserEntityToModelMapper @Inject constructor() {

    fun mapFrom(response: UserEntity) = User(
        id = response.id,
        img = response.img,
        name = response.name,
        username = response.username
    )
}
