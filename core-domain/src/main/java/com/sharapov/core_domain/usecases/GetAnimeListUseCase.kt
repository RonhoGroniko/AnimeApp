package com.sharapov.core_domain.usecases

import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {

    operator fun invoke(rankingType: RankingType): Flow<List<Anime>> {
        return repository.getAnimeList(rankingType)
    }
}