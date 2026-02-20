package com.sharapov.core_domain.entity.filter

import com.sharapov.core_domain.entity.AnimeKind
import com.sharapov.core_domain.entity.AnimeRating
import com.sharapov.core_domain.entity.AnimeStatus


data class AnimeFilter(
    val order: AnimeOrder = AnimeOrder.BY_RANK,
    val kind: AnimeKind? = null,
    val status: AnimeStatus? = null,
    val season: String? = null,
    val rating: AnimeRating? = null,
    val origin: AnimeOrigin? = null,
    val genre: String? = null, // List of comma separated genre ids
    val studio: String? = null, // List of comma separated studio ids
    val franchise: String? = null,
    val censored: Boolean = true
)

// TODO: маппер в ui? и обратно полуается хз
//