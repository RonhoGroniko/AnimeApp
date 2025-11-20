package com.sharapov.domain_anime.repository

import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.entity.RankingType
import com.sharapov.domain_anime.entity.common.AnimeFilter
import kotlinx.coroutines.flow.Flow

interface ListAnimeRepository {

    fun getAnimeList(filter: AnimeFilter): Flow<List<Anime>>

    suspend fun updateAnimeList(rankingType: RankingType, limit: Int)

}