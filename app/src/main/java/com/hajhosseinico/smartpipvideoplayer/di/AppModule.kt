package com.hajhosseinico.smartpipvideoplayer.di

import com.hajhosseinico.smartpipvideoplayer.data.repository.VideoRepositoryImpl
import com.hajhosseinico.smartpipvideoplayer.domain.repository.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindVideoRepository(
        videoRepositoryImpl: VideoRepositoryImpl
    ): VideoRepository
}
