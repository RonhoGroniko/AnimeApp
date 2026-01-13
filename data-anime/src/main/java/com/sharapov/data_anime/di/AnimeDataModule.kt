package com.sharapov.data_anime.di

import com.sharapov.data_anime.repository.AnimeDetailsRepositoryImpl
import com.sharapov.data_anime.repository.AnimeFavoriteRepositoryImpl
import com.sharapov.data_anime.repository.AnimeListRepositoryImpl
import com.sharapov.data_anime.repository.AnimeSearchRepositoryImpl
import com.sharapov.domain_anime.repository.AnimeDetailsRepository
import com.sharapov.domain_anime.repository.AnimeFavoriteRepository
import com.sharapov.domain_anime.repository.AnimeListRepository
import com.sharapov.domain_anime.repository.AnimeSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface AnimeDataModule {

    @Binds
    @Singleton
    fun bindAnimeListRepository(impl: AnimeListRepositoryImpl): AnimeListRepository

    @Binds
    @Singleton
    fun bindAnimeDetailsRepository(impl: AnimeDetailsRepositoryImpl): AnimeDetailsRepository

    @Binds
    @Singleton
    fun bindAnimeSearchRepository(impl: AnimeSearchRepositoryImpl): AnimeSearchRepository

    @Binds
    @Singleton
    fun bindAnimeFavoriteRepository(impl: AnimeFavoriteRepositoryImpl): AnimeFavoriteRepository
}
