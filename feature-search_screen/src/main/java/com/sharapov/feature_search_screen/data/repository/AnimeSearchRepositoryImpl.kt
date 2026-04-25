package com.sharapov.feature_search_screen.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.feature_search_screen.data.paging.AnimeListPagingSource
import com.sharapov.feature_search_screen.domain.entity.AnimeFilter
import com.sharapov.feature_search_screen.domain.repository.AnimeSearchRepository
import javax.inject.Inject

class AnimeSearchRepositoryImpl @Inject constructor(
    private val pagingSourceFactory: AnimeListPagingSource.Factory
) : AnimeSearchRepository {


    override fun searchAnime(
        query: String,
        limit: Int,
        filter: AnimeFilter
    ): Pager<Int, AnimeListItem> {
        return Pager(
            PagingConfig(
                initialLoadSize = 20,
                pageSize = limit,
                enablePlaceholders = false,
                maxSize = 400
            )
        ) {
            pagingSourceFactory.create(
                query = query,
                filter = filter,
                limit = limit
            )
        }
    }
}