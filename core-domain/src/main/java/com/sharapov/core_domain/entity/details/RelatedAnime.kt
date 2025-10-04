package com.sharapov.core_domain.entity.details

import com.sharapov.core_domain.entity.Anime

data class RelatedAnime(
    val anime: Anime,
    val relation: String
)
