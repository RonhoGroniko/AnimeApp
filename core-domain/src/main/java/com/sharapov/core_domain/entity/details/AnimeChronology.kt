package com.sharapov.core_domain.entity.details

import com.sharapov.domain_anime.entity.AnimeKind
import com.sharapov.domain_anime.entity.list.AnimeListItem

data class AnimeChronology(
    val animeListItem: AnimeListItem,
    val kind: AnimeKind
)