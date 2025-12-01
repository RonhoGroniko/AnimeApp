package com.sharapov.feature_search_screen

import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.domain_anime.entity.list.AnimeListItem

data class SearchScreenContent(
    val query: String,
    val animeList: List<AnimeListItem>
)

typealias SearchScreenState = LceState<SearchScreenContent>