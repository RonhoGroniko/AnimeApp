package com.sharapov.feature_main_screen.presentation

import com.sharapov.domain_anime.entity.Anime

sealed interface MainScreenState {

    data object Initial : MainScreenState
    data object Loading : MainScreenState
    data class Content(
        val upcomingList: List<Anime>,
        val airingList: List<Anime>,
        val popularList: List<Anime>
    ) : MainScreenState

    data class Error(val message: String) : MainScreenState
}