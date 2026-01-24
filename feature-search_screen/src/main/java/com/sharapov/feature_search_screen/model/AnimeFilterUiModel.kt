package com.sharapov.feature_search_screen.model

import com.sharapov.domain_anime.entity.AnimeKind
import com.sharapov.domain_anime.entity.AnimeRating
import com.sharapov.domain_anime.entity.AnimeStatus
import com.sharapov.domain_anime.entity.filter.AnimeOrder
import com.sharapov.domain_anime.entity.filter.AnimeOrigin

data class AnimeFilterUiModel(
    val order: AnimeOrder = AnimeOrder.BY_RANK,
    val kind: AnimeKind? = null,
    val status: AnimeStatus? = null,
    val season: String? = null,
    val rating: AnimeRating? = null,
    val origin: AnimeOrigin? = null,
    val genre: List<String>? = null,
    val studio: String? = null,
    val franchise: String? = null,
    val censored: Boolean = true
)
