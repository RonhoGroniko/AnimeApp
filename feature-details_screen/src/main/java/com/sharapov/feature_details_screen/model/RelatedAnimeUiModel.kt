package com.sharapov.feature_details_screen.model

import com.sharapov.core_domain.entity.Anime

data class RelatedAnimeUiModel(
    val anime: Anime,
    val relation: String
)
