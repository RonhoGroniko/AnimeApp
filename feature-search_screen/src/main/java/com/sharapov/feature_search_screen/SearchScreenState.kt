package com.sharapov.feature_search_screen

import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.feature_search_screen.model.AnimeFilterUiModel

data class SearchScreenContent(
    val query: String = "",
    val filter: AnimeFilterUiModel
)

typealias SearchScreenState = LceState<SearchScreenContent>