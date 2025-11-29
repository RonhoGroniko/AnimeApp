package com.sharapov.domain_anime.entity.details

import com.sharapov.domain_anime.entity.AnimeKind

data class AnimeChronology(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val kind: AnimeKind
)