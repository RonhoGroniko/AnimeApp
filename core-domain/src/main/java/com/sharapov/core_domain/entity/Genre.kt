package com.sharapov.core_domain.entity

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
