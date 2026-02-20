package com.sharapov.feature_details_screen.di

import com.sharapov.feature_details_screen.data.repository.AnimeDetailsRepositoryImpl
import com.sharapov.feature_details_screen.domain.repository.AnimeDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FeatureDetailsModule {

    @Binds
    @Singleton
    fun bindAnimeDetailsRepository(impl: AnimeDetailsRepositoryImpl): AnimeDetailsRepository
}