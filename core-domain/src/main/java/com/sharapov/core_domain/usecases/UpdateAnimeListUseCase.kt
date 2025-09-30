package com.sharapov.core_domain.usecases

import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.repository.AnimeRepository
import javax.inject.Inject

class UpdateAnimeListUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {

    suspend operator fun invoke(rankingType: RankingType) {
        animeRepository.updateAnimeList(rankingType)
    }
}