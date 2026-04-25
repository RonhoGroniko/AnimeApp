package com.sharapov.feature_details_screen.domain.usecases

import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.feature_details_screen.domain.repository.AnimeDetailsRepository
import javax.inject.Inject

class ChangeFavoriteStatusUseCase @Inject constructor(
    private val favoriteRepository: AnimeDetailsRepository
) {

    suspend operator fun invoke(anime: AnimeListItem, makeFavorite: Boolean) = favoriteRepository.changeFavoriteStatus(anime, makeFavorite)
}