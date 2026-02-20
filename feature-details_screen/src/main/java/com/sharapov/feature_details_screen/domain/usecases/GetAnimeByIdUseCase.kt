package com.sharapov.feature_details_screen.domain.usecases

import com.sharapov.core_domain.Result
import com.sharapov.feature_details_screen.domain.entity.AnimeDetails
import com.sharapov.feature_details_screen.domain.repository.AnimeDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeByIdUseCase @Inject constructor(
    private val animeDetailsRepository: AnimeDetailsRepository
) {

    operator fun invoke(animeId: Long): Flow<Result<AnimeDetails>> = animeDetailsRepository.getAnimeById(animeId.toString())
}