package com.sharapov.domain_anime.usecases.anime.list

import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {

    operator fun invoke(query: String, filter: AnimeFilter): Flow<List<Anime>> {
        return repository.searchAnimeByTitle(query, filter)
    }
}