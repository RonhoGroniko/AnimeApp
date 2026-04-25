package com.sharapov.feature_search_screen.ui

import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.feature_search_screen.domain.entity.AnimeFilter

data class SearchScreenContent(
    val query: String = "",
    val filter: AnimeFilter
)

typealias SearchScreenState = LceState<SearchScreenContent>