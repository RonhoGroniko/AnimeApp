package com.sharapov.domain_anime.entity

enum class RankingType(val query: String) {

    ALL("all"),
    UPCOMING("upcoming"),
    AIRING("airing"),
    BY_POPULARITY("bypopularity")
}