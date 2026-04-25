package com.sharapov.feature_favorites_screen.di

import com.sharapov.feature_favorites_screen.data.repository.AnimeFavoriteRepositoryImpl
import com.sharapov.feature_favorites_screen.domain.repository.AnimeFavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FeatureFavoritesModule {

    @Binds
    @Singleton
    fun bindAnimeFavoriteRepository(impl: AnimeFavoriteRepositoryImpl): AnimeFavoriteRepository
}