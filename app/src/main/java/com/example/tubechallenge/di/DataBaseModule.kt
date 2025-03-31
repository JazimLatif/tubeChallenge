package com.example.tubechallenge.di

import android.content.Context
import androidx.room.Room
import com.example.tubechallenge.data.db.StopDao
import com.example.tubechallenge.data.db.StopDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideStopDatabase(@ApplicationContext context: Context): StopDatabase {
        return Room.databaseBuilder(
            context,
            StopDatabase::class.java,
            "tube-challenge.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideStopDao(stopDatabase: StopDatabase): StopDao =
        stopDatabase.stopDao()
}