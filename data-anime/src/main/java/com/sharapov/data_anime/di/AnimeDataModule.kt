package com.sharapov.data_anime.di

import com.sharapov.data_anime.repository.DetailsAnimeRepositoryImpl
import com.sharapov.data_anime.repository.ListAnimeRepositoryImpl
import com.sharapov.data_anime.repository.SearchAnimeRepositoryImpl
import com.sharapov.domain_anime.repository.DetailsAnimeRepository
import com.sharapov.domain_anime.repository.ListAnimeRepository
import com.sharapov.domain_anime.repository.SearchAnimeRepository
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
    fun bindListAnimeRepository(impl: ListAnimeRepositoryImpl): ListAnimeRepository

    @Binds
    @Singleton
    fun bindSearchAnimeRepository(impl: SearchAnimeRepositoryImpl): SearchAnimeRepository

    @Binds
    @Singleton
    fun bindDetailsAnimeRepository(impl: DetailsAnimeRepositoryImpl): DetailsAnimeRepository
}
