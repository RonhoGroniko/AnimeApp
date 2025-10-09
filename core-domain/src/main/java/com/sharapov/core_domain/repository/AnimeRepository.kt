package com.sharapov.core_domain.repository

import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.entity.details.AnimeWithDetails
import com.sharapov.core_domain.usecases.AnimeFilter
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun getAnimeList(filter: AnimeFilter): Flow<List<Anime>>

    suspend fun updateAnimeList(rankingType: RankingType, limit: Int)

    suspend fun getAnimeById(animeId: Int): AnimeWithDetails
}