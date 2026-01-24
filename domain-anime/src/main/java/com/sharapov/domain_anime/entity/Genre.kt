package com.sharapov.domain_anime.entity

data class Genre(
    val id: Long,
    val name: String,
    val kind: GenreKind
)

enum class GenreKind(val value: String) {
    DEMOGRAPHIC("Demographic"),
    GENRE("Genre"),
    THEME("Theme"),
    UNKNOWN("Unknown")
}
