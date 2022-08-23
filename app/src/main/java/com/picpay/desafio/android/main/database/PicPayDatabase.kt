package com.picpay.desafio.android.main.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.users.data.database.UsersDao
import com.picpay.desafio.android.users.data.database.entity.UserEntity

private const val DATABASE_VERSION = 1

@Database(entities = [UserEntity::class], version = DATABASE_VERSION)
abstract class PicPayDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao
}
