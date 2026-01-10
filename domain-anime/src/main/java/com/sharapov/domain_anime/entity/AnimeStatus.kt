package com.sharapov.domain_anime.entity

enum class AnimeStatus(val value: String, val valueForUi: String) {
    ANONS("anons", "Planned"),
    ONGOING("ongoing", "Airing"),
    RELEASED("released", "Released"),
    UNKNOWN("unknown", "")
}