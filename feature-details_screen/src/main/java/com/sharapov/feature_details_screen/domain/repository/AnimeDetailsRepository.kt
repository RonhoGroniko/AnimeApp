package com.sharapov.feature_details_screen.domain.repository

import com.sharapov.core_domain.Result
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.feature_details_screen.domain.entity.AnimeDetails
import kotlinx.coroutines.flow.Flow

interface AnimeDetailsRepository {

    fun getAnimeById(animeId: String) : Flow<Result<AnimeDetails>>

    suspend fun changeFavoriteStatus(anime: AnimeListItem, makeFavorite: Boolean) : Result<Unit>

    fun getFavoriteStatus(animeId: Long) : Flow<Result<Boolean>>
}