package com.picpay.desafio.android.users.domain.model

sealed class UserError {
    object GenericError : UserError()
    class Fallback(val fallbackUsers: List<User>) : UserError()
}
