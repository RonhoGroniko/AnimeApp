package com.sharapov.domain_anime.usecases.search

import androidx.paging.Pager
import com.sharapov.domain_anime.entity.filter.AnimeFilter
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.domain_anime.repository.AnimeSearchRepository
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(
    private val searchRepository: AnimeSearchRepository
) {

    operator fun invoke(
        query: String,
        limit: Int,
        page: Int,
        filter: AnimeFilter
    ): Pager<Int, AnimeListItem> {
        return searchRepository.searchAnime(
            query = query,
            limit = limit,
            page = page,
            filter = filter
        )
    }
}