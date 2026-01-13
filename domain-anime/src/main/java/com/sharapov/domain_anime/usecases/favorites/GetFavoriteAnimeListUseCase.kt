package com.sharapov.domain_anime.usecases.favorites

import com.sharapov.core_domain.Result
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.domain_anime.repository.AnimeFavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteAnimeListUseCase @Inject constructor(
    private val favoriteRepository: AnimeFavoriteRepository
) {

    operator fun invoke(): Flow<Result<List<AnimeListItem>>> =
        favoriteRepository.getFavoriteAnimeList()
}
