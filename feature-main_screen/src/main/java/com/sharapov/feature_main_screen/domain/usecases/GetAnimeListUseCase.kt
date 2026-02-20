package com.sharapov.feature_main_screen.domain.usecases

import com.sharapov.core_domain.entity.AnimeStatus
import com.sharapov.feature_main_screen.domain.repository.AnimeListRepository
import javax.inject.Inject

class GetAnimeListUseCase @Inject constructor(
    private val animeListRepository: AnimeListRepository
) {

    operator fun invoke(animeStatus: AnimeStatus, limit: Int) = animeListRepository.getAnimeList(animeStatus, limit)
}