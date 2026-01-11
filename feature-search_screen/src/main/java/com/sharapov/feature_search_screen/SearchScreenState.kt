package com.sharapov.feature_search_screen

import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.domain_anime.entity.filter.AnimeFilter

data class SearchScreenContent(
    val query: String = "",
    val filter: AnimeFilter
)

typealias SearchScreenState = LceState<SearchScreenContent>