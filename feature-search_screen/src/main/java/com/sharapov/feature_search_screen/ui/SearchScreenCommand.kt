package com.sharapov.feature_search_screen.ui

import com.sharapov.feature_search_screen.domain.entity.AnimeFilter

interface SearchScreenCommand {

    data class ChangeQuery(val query: String) : SearchScreenCommand

    data class ApplyFilter(val filter: AnimeFilter) : SearchScreenCommand
}