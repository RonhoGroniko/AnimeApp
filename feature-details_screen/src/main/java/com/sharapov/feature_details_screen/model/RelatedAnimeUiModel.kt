package com.sharapov.feature_details_screen.model

import com.sharapov.domain_anime.entity.Anime

data class RelatedAnimeUiModel(
    val anime: Anime,
    val relation: String
)
