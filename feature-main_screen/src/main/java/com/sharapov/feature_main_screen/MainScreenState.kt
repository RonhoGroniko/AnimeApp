package com.sharapov.feature_main_screen

import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.SectionState
import com.sharapov.domain_anime.entity.AnimeListItem

typealias MainScreenState = LceState<MainScreenContent>

data class MainScreenContent(
    val upcomingAnimeList: SectionState<List<AnimeListItem>>,
    val airingAnimeList: SectionState<List<AnimeListItem>>
)