package com.sharapov.feature_search_screen

import com.sharapov.core_domain.entity.Anime

sealed interface SearchScreenState {

    data object Initial: SearchScreenState
    data object Loading: SearchScreenState
    data class Error(val message: String): SearchScreenState
    data class Content(
        val query: String,
        val animeList: List<Anime>
    ): SearchScreenState
}