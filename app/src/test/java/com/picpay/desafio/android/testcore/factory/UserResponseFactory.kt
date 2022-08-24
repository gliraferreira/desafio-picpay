package com.picpay.desafio.android.testcore.factory

import com.picpay.desafio.android.testcore.RandomUtil
import com.picpay.desafio.android.users.data.remote.api.response.UserResponse

object UserResponseFactory {

    fun makeUserResponse(
        id: String = RandomUtil.uuid(),
        img: String = RandomUtil.string(),
        name: String = RandomUtil.firstName(),
        username: String = RandomUtil.name()
    ) = UserResponse(
        id = id,
        img = img,
        name = name,
        username = username
    )
}