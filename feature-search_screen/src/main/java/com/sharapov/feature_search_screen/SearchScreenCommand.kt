package com.sharapov.feature_search_screen

sealed interface SearchScreenCommand {

    data class ChangeQuery(val query: String): SearchScreenCommand
}