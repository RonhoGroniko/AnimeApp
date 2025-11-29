package com.sharapov.network_anime.mapper

import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.network_anime.fragment.AnimeFields

fun AnimeFields.toEntity() : AnimeListItem {
    return AnimeListItem(
        id = id.toLong(),
        name = name,
        score = score ?: 0.0,
        imageUrl = poster?.originalUrl ?: ""
    )
}