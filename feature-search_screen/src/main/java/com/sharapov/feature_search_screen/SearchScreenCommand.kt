package com.sharapov.feature_search_screen

import com.sharapov.domain_anime.entity.filter.AnimeFilter

interface SearchScreenCommand {

    data class ChangeQuery(val query: String) : SearchScreenCommand

    data class ApplyFilter(val filter: AnimeFilter) : SearchScreenCommand
}