package com.sharapov.feature_favorites_screen.domain.usecases

import com.sharapov.core_domain.Result
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.feature_favorites_screen.domain.repository.AnimeFavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteAnimeListUseCase @Inject constructor(
    private val favoriteRepository: AnimeFavoriteRepository
) {

    operator fun invoke(): Flow<Result<List<AnimeListItem>>> =
        favoriteRepository.getFavoriteAnimeList()
}