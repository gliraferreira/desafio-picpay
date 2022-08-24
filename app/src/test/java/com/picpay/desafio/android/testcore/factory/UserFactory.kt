package com.picpay.desafio.android.testcore.factory

import com.picpay.desafio.android.testcore.RandomUtil
import com.picpay.desafio.android.users.domain.model.User

object UserFactory {

    fun makeUser(
        id: String = RandomUtil.uuid(),
        img: String = RandomUtil.string(),
        name: String = RandomUtil.firstName(),
        username: String = RandomUtil.name()
    ) = User(
        id = id,
        img = img,
        name = name,
        username = username
    )
}