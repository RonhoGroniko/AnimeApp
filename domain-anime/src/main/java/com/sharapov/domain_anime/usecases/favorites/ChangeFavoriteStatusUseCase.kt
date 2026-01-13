package com.sharapov.domain_anime.usecases.favorites

import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.domain_anime.repository.AnimeFavoriteRepository
import javax.inject.Inject

class ChangeFavoriteStatusUseCase @Inject constructor(
    private val favoriteRepository: AnimeFavoriteRepository
) {

    suspend operator fun invoke(anime: AnimeListItem, makeFavorite: Boolean) = favoriteRepository.changeFavoriteStatus(anime, makeFavorite)
}