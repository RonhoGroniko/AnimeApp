package com.sharapov.core_data.repository

import com.sharapov.core_data.remote.retrofit.AnimeApiService
import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor (
    private val animeApiService: AnimeApiService
): AnimeRepository {

    override fun getAnimeList(): Flow<List<Anime>> {
        TODO()
    }

    override suspend fun loadAnimeList() {
        TODO()
    }
}