package com.sharapov.feature_search_screen.di

import com.sharapov.feature_search_screen.data.repository.AnimeSearchRepositoryImpl
import com.sharapov.feature_search_screen.domain.repository.AnimeSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FeatureSearchModule {

    @Binds
    @Singleton
    fun bindAnimeSearchRepository(impl: AnimeSearchRepositoryImpl): AnimeSearchRepository
}