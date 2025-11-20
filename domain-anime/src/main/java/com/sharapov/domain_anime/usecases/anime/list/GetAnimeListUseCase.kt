package com.sharapov.domain_anime.usecases.anime.list

import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.repository.ListAnimeRepository
import com.sharapov.domain_anime.entity.common.AnimeFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeListUseCase @Inject constructor(
    private val repository: ListAnimeRepository
) {

    operator fun invoke(filter: AnimeFilter): Flow<List<Anime>> {
        return repository.getAnimeList(filter)
    }
}