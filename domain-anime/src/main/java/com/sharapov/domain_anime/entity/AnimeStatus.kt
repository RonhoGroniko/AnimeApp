package com.sharapov.domain_anime.entity

enum class AnimeStatus(val value: String, val valueForUi: String) {
    ANONS("anons", "Planned"),
    ONGOING("ongoing", "Airing"),
    RELEASED("released", "Released"),
    UNKNOWN("unknown", "");

    companion object {

        fun valueOfUi(valueForUi: String): AnimeStatus {
            return when (valueForUi) {
                "Planned" -> ANONS
                "Airing" -> ONGOING
                "Released" -> RELEASED
                "" -> UNKNOWN
                else -> throw IllegalArgumentException("No enum constant for $valueForUi")
            }
        }
    }
}