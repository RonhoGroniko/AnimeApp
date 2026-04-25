package com.sharapov.feature_favorites_screen.domain.repository

import com.sharapov.core_domain.Result
import com.sharapov.core_domain.entity.list.AnimeListItem
import kotlinx.coroutines.flow.Flow

interface AnimeFavoriteRepository {

    fun getFavoriteAnimeList(): Flow<Result<List<AnimeListItem>>>
}