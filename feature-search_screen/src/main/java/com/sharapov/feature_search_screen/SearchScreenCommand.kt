package com.sharapov.feature_search_screen

sealed interface SearchScreenCommand {

    data class ChangeQuery(val query: String): SearchScreenCommand
    data class Search(val query: String): SearchScreenCommand
}