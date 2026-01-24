package com.sharapov.domain_anime.entity.details

data class StatusStats(
    val count: Int,
    val kind: StatusKind
)

enum class StatusKind(val value: String) {
    PLANNED("Planned"),
    WATCHING("Watching"),
    REWATCHING("Rewatching"),
    COMPLETED("Completed"),
    ON_HOLD("On hold"),
    DROPPED("Dropped"),
    UNKNOWN("Unknown")
}
