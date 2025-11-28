package com.sharapov.network_anime.mapper

import com.sharapov.domain_anime.entity.AnimeListItem
import com.sharapov.network_anime.GetAnimeListQuery

fun GetAnimeListQuery.Anime.toEntity() = AnimeListItem(
    id = animeFields.id.toLong(),
    name = animeFields.name,
    score = animeFields.score ?: 0.0,
    imageUrl = animeFields.poster?.originalUrl ?: TODO("Make placeholder imageUrl")
)