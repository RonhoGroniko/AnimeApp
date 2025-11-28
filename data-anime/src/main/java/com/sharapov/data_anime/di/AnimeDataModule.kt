package com.sharapov.data_anime.di

import com.sharapov.data_anime.repository.AnimeListRepositoryImpl
import com.sharapov.domain_anime.repository.AnimeListRepository
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
}
