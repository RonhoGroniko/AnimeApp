package com.sharapov.domain_anime.usecases

import com.sharapov.core_domain.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteAnimeListUseCase @Inject constructor(
    private val favoriteRepository: AnimeFavoriteRepository
) {

    operator fun invoke(): Flow<Result<List<AnimeListItem>>> =
        favoriteRepository.getFavoriteAnimeList()
}