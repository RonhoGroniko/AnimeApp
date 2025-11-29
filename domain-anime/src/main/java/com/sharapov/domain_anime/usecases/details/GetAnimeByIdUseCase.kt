package com.sharapov.domain_anime.usecases.details

import com.sharapov.core_domain.Result
import com.sharapov.domain_anime.entity.details.AnimeDetails
import com.sharapov.domain_anime.repository.AnimeDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeByIdUseCase @Inject constructor(
    private val animeDetailsRepository: AnimeDetailsRepository
) {

    operator fun invoke(animeId: Long): Flow<Result<AnimeDetails>> = animeDetailsRepository.getAnimeById(animeId.toString())
}