package com.sharapov.domain_anime.repository

import androidx.paging.Pager
import com.sharapov.core_domain.entity.filter.AnimeFilter
import com.sharapov.core_domain.entity.list.AnimeListItem

interface AnimeSearchRepository {

    fun searchAnime(
        query: String,
        limit: Int,
        filter: AnimeFilter
    ) : Pager<Int, AnimeListItem>
}