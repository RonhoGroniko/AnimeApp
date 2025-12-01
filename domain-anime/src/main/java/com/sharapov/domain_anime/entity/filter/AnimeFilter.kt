package com.sharapov.domain_anime.entity.filter

import com.sharapov.domain_anime.entity.AnimeKind
import com.sharapov.domain_anime.entity.AnimeRating
import com.sharapov.domain_anime.entity.AnimeStatus

data class AnimeFilter(
    val order: AnimeOrder = AnimeOrder.BY_RANK,
    val kind: AnimeKind? = null,
    val status: AnimeStatus? = null,
    val season: String? = null,
    val rating: AnimeRating? = null,
    val origin: AnimeOrigin? = null,
    val genre: String? = null,
    val studio: String? = null,
    val franchise: String? = null,
    val censored: Boolean? = null
)