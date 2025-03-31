package com.example.tubechallenge.di

import com.example.tubechallenge.data.repository.StopRepositoryImpl
import com.example.tubechallenge.domain.repository.StopRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindStopRepository(
        stopRepositoryImpl: StopRepositoryImpl
    ): StopRepository
}