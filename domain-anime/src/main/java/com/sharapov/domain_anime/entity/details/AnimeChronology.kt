package com.sharapov.domain_anime.entity.details

import com.sharapov.domain_anime.entity.AnimeKind
import com.sharapov.domain_anime.entity.list.AnimeListItem

data class AnimeChronology(
    val animeListItem: AnimeListItem,
    val kind: AnimeKind
)