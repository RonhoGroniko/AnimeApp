package com.sharapov.core_network.mapper

import com.sharapov.core_domain.entity.list.AnimeListItem
import schema.fragment.AnimeFields

fun AnimeFields.toEntity() : AnimeListItem {
    return AnimeListItem(
        id = id.toLong(),
        name = name,
        score = score ?: 0.0,
        imageUrl = poster?.originalUrl ?: ""
    )
}