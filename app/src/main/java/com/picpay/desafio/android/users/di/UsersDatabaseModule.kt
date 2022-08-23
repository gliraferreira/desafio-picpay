package com.picpay.desafio.android.users.di

import com.picpay.desafio.android.main.database.PicPayDatabase
import com.picpay.desafio.android.users.data.database.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersDatabaseModule {

    @Singleton
    @Provides
    fun provideUsersDao(picPayDatabase: PicPayDatabase): UsersDao {
        return picPayDatabase.usersDao()
    }
}
