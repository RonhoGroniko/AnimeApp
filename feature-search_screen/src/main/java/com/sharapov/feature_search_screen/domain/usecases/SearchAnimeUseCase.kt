package com.sharapov.feature_search_screen.domain.usecases

import androidx.paging.Pager
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.feature_search_screen.domain.entity.AnimeFilter
import com.sharapov.feature_search_screen.domain.repository.AnimeSearchRepository
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