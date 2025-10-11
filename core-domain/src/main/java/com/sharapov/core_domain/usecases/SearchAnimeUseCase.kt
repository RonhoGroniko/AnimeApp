package com.sharapov.core_domain.usecases

import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.repository.AnimeRepository
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val repository: AnimeRepository
) {

    suspend operator fun invoke(query: String): List<Anime> {
        return repository.searchAnimeByTitle(query)
    }
}