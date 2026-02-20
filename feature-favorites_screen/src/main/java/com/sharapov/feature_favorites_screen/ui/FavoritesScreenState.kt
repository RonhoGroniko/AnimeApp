package com.sharapov.feature_favorites_screen.ui

import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.core_ui.theme.core.LceState

data class FavoritesScreenContent(
    val animeList: List<AnimeListItem>
)

typealias FavoritesScreenState = LceState<FavoritesScreenContent>