package com.sharapov.domain_anime.repository

import com.sharapov.core_domain.Result
import com.sharapov.domain_anime.entity.details.AnimeDetails
import kotlinx.coroutines.flow.Flow

interface AnimeDetailsRepository {

    fun getAnimeById(animeId: String) : Flow<Result<AnimeDetails>>
}