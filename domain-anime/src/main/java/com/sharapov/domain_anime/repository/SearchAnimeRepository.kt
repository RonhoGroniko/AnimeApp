package com.sharapov.domain_anime.repository

import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.entity.common.AnimeFilter
import kotlinx.coroutines.flow.Flow

interface SearchAnimeRepository {

    fun searchAnimeByTitle(query: String, filter: AnimeFilter): Flow<List<Anime>>
}