package com.sharapov.feature_main_screen.domain.repository

import com.sharapov.core_domain.Result
import com.sharapov.core_domain.entity.AnimeStatus
import com.sharapov.core_domain.entity.list.AnimeListItem
import kotlinx.coroutines.flow.Flow

interface AnimeListRepository {

    fun getAnimeList(animeStatus: AnimeStatus, limit: Int): Flow<Result<List<AnimeListItem>>>
}