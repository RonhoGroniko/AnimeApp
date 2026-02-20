package com.sharapov.core_domain.entity.details

import com.sharapov.core_domain.entity.list.AnimeListItem


data class RelatedAnime(
    val animeListItem: AnimeListItem,
    val relationKind: RelationKind
)


enum class RelationKind(val value: String) {
    ADAPTATION("Adaptation"),
    ALTERNATIVE_SETTING("Alternative Setting"),
    ALTERNATIVE_VERSION("Alternative Version"),
    CHARACTER("Character"),
    FULL_STORY("Full Story"),
    OTHER("Other"),
    PARENT_STORY("Parent Story"),
    PREQUEL("Prequel"),
    SEQUEL("Sequel"),
    SIDE_STORY("Side Story"),
    SPIN_OFF("Spin-off"),
    SUMMARY("Summary"),
    UNKNOWN("Unknown"),
}