package com.picpay.desafio.android.users.data.remote.api.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    val id: String,
    val img: String,
    val name: String,
    val username: String
)
