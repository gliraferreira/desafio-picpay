package com.picpay.desafio.android.users.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val img: String,
    val name: String,
    val username: String
)
