package com.sharapov.feature_recommendations_screen.ui

import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.core_ui.theme.core.LceState

data class RecommendScreenContent(
    val animeList: List<AnimeListItem>
)

typealias RecommendScreenState = LceState<RecommendScreenContent>