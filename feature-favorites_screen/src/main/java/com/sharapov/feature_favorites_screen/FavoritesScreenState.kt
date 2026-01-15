package com.sharapov.feature_favorites_screen

import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.domain_anime.entity.list.AnimeListItem

data class FavoritesScreenContent(
    val animeList: List<AnimeListItem>
)

typealias FavoritesScreenState = LceState<FavoritesScreenContent>