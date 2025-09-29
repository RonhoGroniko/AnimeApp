package com.sharapov.core_domain.repository

import com.sharapov.core_domain.entity.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun getAnimeList(): Flow<List<Anime>>
}