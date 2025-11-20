package com.sharapov.domain_anime.usecases.anime.list

import com.sharapov.domain_anime.entity.RankingType
import com.sharapov.domain_anime.repository.ListAnimeRepository
import javax.inject.Inject

class UpdateAnimeListUseCase @Inject constructor(
    private val listAnimeRepository: ListAnimeRepository
) {

    suspend operator fun invoke(rankingType: RankingType, limit: Int): Result<Unit> {
        return runCatching { listAnimeRepository.updateAnimeList(rankingType, limit) }
    }
}