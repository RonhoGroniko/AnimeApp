package com.sharapov.feature_details_screen.domain.entity

import com.sharapov.core_domain.entity.AnimeKind
import com.sharapov.core_domain.entity.list.AnimeListItem

data class AnimeChronology(
    val animeListItem: AnimeListItem,
    val kind: AnimeKind
)