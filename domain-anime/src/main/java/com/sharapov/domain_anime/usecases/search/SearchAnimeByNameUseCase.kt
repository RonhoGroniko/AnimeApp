package com.sharapov.domain_anime.usecases.search

import com.sharapov.domain_anime.repository.AnimeSearchRepository
import javax.inject.Inject

class SearchAnimeByNameUseCase @Inject constructor(
    private val searchRepository: AnimeSearchRepository
) {
}