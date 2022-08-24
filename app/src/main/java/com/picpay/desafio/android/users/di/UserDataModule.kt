package com.picpay.desafio.android.users.di

import com.picpay.desafio.android.users.data.local.datasource.UsersCacheDataSource
import com.picpay.desafio.android.users.data.local.datasource.UsersLocalDataSource
import com.picpay.desafio.android.users.data.remote.datasource.UsersRemoteDataSource
import com.picpay.desafio.android.users.data.remote.datasource.UsersServiceDataSource
import com.picpay.desafio.android.users.data.repository.UsersDefaultRepository
import com.picpay.desafio.android.users.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserDataModule {

    @Binds
    @Reusable
    fun bindsUsersRemoteDataSource(
        serviceDataSource: UsersServiceDataSource
    ): UsersRemoteDataSource

    @Binds
    @Reusable
    fun bindsUsersLocalDataSource(
        localDataSource: UsersCacheDataSource
    ): UsersLocalDataSource

    @Binds
    @Reusable
    fun bindsUsersRepository(
        defaultRepository: UsersDefaultRepository
    ): UsersRepository
}
