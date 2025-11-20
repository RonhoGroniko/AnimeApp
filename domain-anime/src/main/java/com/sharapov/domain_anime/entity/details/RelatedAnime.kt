package com.sharapov.domain_anime.entity.details

import com.sharapov.domain_anime.entity.Anime

data class RelatedAnime(
    val anime: Anime,
    val relation: RelationType
)
