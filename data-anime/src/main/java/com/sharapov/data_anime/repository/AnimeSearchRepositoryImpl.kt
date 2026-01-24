package com.sharapov.data_anime.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sharapov.data_anime.paging.AnimeListPagingSource
import com.sharapov.domain_anime.entity.filter.AnimeFilter
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.domain_anime.repository.AnimeSearchRepository
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