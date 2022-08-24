package com.picpay.desafio.android.users.data.remote.api

import com.picpay.desafio.android.users.data.remote.api.response.UserResponse
import retrofit2.http.GET

interface UsersApi {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}
