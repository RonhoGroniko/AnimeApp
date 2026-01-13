package com.sharapov.domain_anime.repository

import com.sharapov.core_domain.Result
import com.sharapov.domain_anime.entity.list.AnimeListItem
import kotlinx.coroutines.flow.Flow

interface AnimeFavoriteRepository {

    fun getFavoriteAnimeList(): Flow<Result<List<AnimeListItem>>>

    suspend fun changeFavoriteStatus(anime: AnimeListItem, makeFavorite: Boolean) : Result<Unit>

    fun getFavoriteStatus(animeId: Long) : Flow<Result<Boolean>>
}