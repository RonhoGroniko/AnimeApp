package com.sharapov.data_anime.di

import com.sharapov.data_anime.repository.AnimeRepositoryImpl
import com.sharapov.domain_anime.repository.AnimeRepository
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
    fun bindRepository(impl: AnimeRepositoryImpl): AnimeRepository
}
