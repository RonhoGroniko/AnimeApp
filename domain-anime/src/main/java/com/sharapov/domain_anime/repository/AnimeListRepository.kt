package com.sharapov.domain_anime.repository

import com.sharapov.core_domain.Result
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.domain_anime.entity.AnimeStatus
import kotlinx.coroutines.flow.Flow

interface AnimeListRepository {

    fun getAnimeList(animeStatus: AnimeStatus = AnimeStatus.RELEASED, limit: Int = 8): Flow<Result<List<AnimeListItem>>>
}