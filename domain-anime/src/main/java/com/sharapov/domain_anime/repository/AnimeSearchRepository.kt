package com.sharapov.domain_anime.repository

import androidx.paging.Pager
import com.sharapov.core_domain.Result
import com.sharapov.domain_anime.entity.filter.AnimeFilter
import com.sharapov.domain_anime.entity.list.AnimeListItem
import kotlinx.coroutines.flow.Flow

interface AnimeSearchRepository {

    fun searchAnime(
        query: String,
        limit: Int,
        page: Int,
        filter: AnimeFilter
    ) : Pager<Int, AnimeListItem>
}