package com.sharapov.domain_anime.usecases.anime.list

import com.sharapov.domain_anime.entity.RankingType
import com.sharapov.domain_anime.repository.AnimeRepository
import javax.inject.Inject

class UpdateAnimeListUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {

    suspend operator fun invoke(rankingType: RankingType, limit: Int): Result<Unit> {
        return runCatching { animeRepository.updateAnimeList(rankingType, limit) }
    }
}