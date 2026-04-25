package com.sharapov.feature_search_screen.domain.repository

import androidx.paging.Pager
import com.sharapov.feature_search_screen.domain.entity.AnimeFilter
import com.sharapov.core_domain.entity.list.AnimeListItem

interface AnimeSearchRepository {

    fun searchAnime(
        query: String,
        limit: Int,
        filter: AnimeFilter
    ) : Pager<Int, AnimeListItem>
}