package com.picpay.desafio.android.users.data.local.mapper

import com.picpay.desafio.android.users.data.local.database.entity.UserEntity
import com.picpay.desafio.android.users.domain.model.User
import javax.inject.Inject

class UserModelToEntityMapper @Inject constructor() {

    fun mapFrom(response: User) = UserEntity(
        id = response.id,
        img = response.img,
        name = response.name,
        username = response.username
    )
}
