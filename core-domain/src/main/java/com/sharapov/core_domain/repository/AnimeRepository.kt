package com.sharapov.core_domain.repository

import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.entity.details.AnimeWithDetails
import com.sharapov.core_domain.usecases.AnimeFilter
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun getAnimeList(filter: AnimeFilter): Flow<List<Anime>>

    suspend fun updateAnimeList(rankingType: RankingType, limit: Int)

    fun getAnimeById(animeId: Int): Flow<AnimeWithDetails>

    fun searchAnimeByTitle(query: String, filter: AnimeFilter): Flow<List<Anime>>

    suspend fun changeAnimeFavoriteStatus(animeId: Int)

    suspend fun loadAnimeById(animeId: Int)
}