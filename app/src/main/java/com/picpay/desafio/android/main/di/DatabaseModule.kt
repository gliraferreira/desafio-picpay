package com.picpay.desafio.android.main.di

import android.content.Context
import androidx.room.Room
import com.picpay.desafio.android.main.database.PicPayDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): PicPayDatabase = Room.databaseBuilder(
        appContext,
        PicPayDatabase::class.java,
        "picpay"
    ).build()
}
