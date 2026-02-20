package com.sharapov.domain_anime.usecases

import androidx.paging.Pager
import com.sharapov.domain_anime.repository.AnimeSearchRepository
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val searchRepository: AnimeSearchRepository
) {

    operator fun invoke(
        query: String,
        limit: Int,
        filter: AnimeFilter
    ): Pager<Int, AnimeListItem> {
        return searchRepository.searchAnime(
            query = query,
            limit = limit,
            filter = filter
        )
    }
}