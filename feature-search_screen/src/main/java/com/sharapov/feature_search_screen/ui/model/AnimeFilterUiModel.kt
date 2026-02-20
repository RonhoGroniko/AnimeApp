package com.sharapov.feature_search_screen.ui.model

import com.sharapov.core_domain.entity.AnimeKind
import com.sharapov.core_domain.entity.AnimeRating
import com.sharapov.feature_search_screen.domain.entity.AnimeOrder
import com.sharapov.feature_search_screen.domain.entity.AnimeOrigin

data class AnimeFilterUiModel(
    val order: AnimeOrder = AnimeOrder.BY_RANK,
    val kind: AnimeKind? = null,
    val status: AnimeStatusUiModel? = null,
    val season: String? = null,
    val rating: AnimeRating? = null,
    val origin: AnimeOrigin? = null,
    val genre: List<String>? = null,
    val studio: String? = null,
    val franchise: String? = null,
    val censored: Boolean = true
)
