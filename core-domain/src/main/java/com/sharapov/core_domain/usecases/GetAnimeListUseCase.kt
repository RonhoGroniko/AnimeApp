package com.sharapov.core_domain.usecases

import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeListUseCase @Inject constructor(
    private val repository: AnimeRepository
) {

    operator fun invoke(filter: AnimeFilter): Flow<List<Anime>> {
        return repository.getAnimeList(filter)
    }
}

sealed interface AnimeFilter {
    data object All : AnimeFilter
    data object Favorites: AnimeFilter
    data class ByGenre(val genre: String) : AnimeFilter
    data class ByRankingType(val rankingType: RankingType) : AnimeFilter
}