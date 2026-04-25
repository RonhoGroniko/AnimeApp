package com.sharapov.feature_main_screen.ui

import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.SectionState

typealias MainScreenState = LceState<MainScreenContent>

data class MainScreenContent(
    val upcomingAnimeList: SectionState<List<AnimeListItem>>,
    val airingAnimeList: SectionState<List<AnimeListItem>>,
    val releasedAnimeList: SectionState<List<AnimeListItem>>
)