package com.sharapov.feature_details_screen.domain.usecases

import com.sharapov.feature_details_screen.domain.repository.AnimeDetailsRepository
import javax.inject.Inject

class GetFavoriteStatusUseCase @Inject constructor(
    private val favoriteRepository: AnimeDetailsRepository
) {

    operator fun invoke(animeId: Long) = favoriteRepository.getFavoriteStatus(animeId)
}