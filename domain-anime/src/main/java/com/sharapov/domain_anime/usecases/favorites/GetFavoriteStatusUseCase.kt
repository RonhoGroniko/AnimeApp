package com.sharapov.domain_anime.usecases.favorites

import com.sharapov.domain_anime.repository.AnimeFavoriteRepository
import javax.inject.Inject

class GetFavoriteStatusUseCase @Inject constructor(
    private val favoriteRepository: AnimeFavoriteRepository
) {

    operator fun invoke(animeId: Long) = favoriteRepository.getFavoriteStatus(animeId)
}