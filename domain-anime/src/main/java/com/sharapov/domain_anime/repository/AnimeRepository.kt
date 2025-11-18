package com.sharapov.domain_anime.repository

import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.entity.RankingType
import com.sharapov.domain_anime.entity.details.AnimeWithDetails
import com.sharapov.domain_anime.usecases.anime.list.AnimeFilter
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun getAnimeList(filter: AnimeFilter): Flow<List<Anime>>

    suspend fun updateAnimeList(rankingType: RankingType, limit: Int)

    fun getAnimeById(animeId: Int): Flow<AnimeWithDetails>

    fun searchAnimeByTitle(query: String, filter: AnimeFilter): Flow<List<Anime>>

    suspend fun changeAnimeFavoriteStatus(animeId: Int)

    suspend fun loadAnimeById(animeId: Int)
}