package com.sharapov.domain_anime.repository

import com.sharapov.domain_anime.entity.details.AnimeWithDetails
import kotlinx.coroutines.flow.Flow

interface DetailsAnimeRepository {

    fun getAnimeById(animeId: Int): Flow<AnimeWithDetails>

    suspend fun changeAnimeFavoriteStatus(animeId: Int)

    suspend fun loadAnimeById(animeId: Int)
}