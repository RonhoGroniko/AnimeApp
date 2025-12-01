package com.sharapov.feature_search_screen

interface SearchScreenCommand {

    data class ChangeQuery(val query: String) : SearchScreenCommand
}