package com.sharapov.network_anime.mapper

import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.network_anime.GetAnimeListQuery

fun GetAnimeListQuery.Data.toEntity(): List<AnimeListItem> {
    return animes.map { it.animeFields.toEntity() }
}