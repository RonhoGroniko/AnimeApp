package com.sharapov.feature_main_screen.di

import com.sharapov.feature_main_screen.data.repository.AnimeListRepositoryImpl
import com.sharapov.feature_main_screen.domain.repository.AnimeListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FeatureMainModule {

    @Singleton
    @Binds
    fun bindAnimeListRepository(impl: AnimeListRepositoryImpl): AnimeListRepository
}