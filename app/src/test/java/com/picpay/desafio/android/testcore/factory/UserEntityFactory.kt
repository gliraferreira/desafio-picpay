package com.picpay.desafio.android.testcore.factory

import com.picpay.desafio.android.testcore.RandomUtil
import com.picpay.desafio.android.users.data.local.database.entity.UserEntity

object UserEntityFactory {

    fun makeUserEntity(
        id: String = RandomUtil.uuid(),
        img: String = RandomUtil.string(),
        name: String = RandomUtil.firstName(),
        username: String = RandomUtil.name()
    ) = UserEntity(
        id = id,
        img = img,
        name = name,
        username = username
    )
}
