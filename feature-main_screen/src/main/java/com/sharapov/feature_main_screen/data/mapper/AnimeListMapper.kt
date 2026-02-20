package com.sharapov.feature_main_screen.data.mapper

import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.core_network.mapper.toEntity
import `feature-main_screen`.GetAnimeListQuery

fun GetAnimeListQuery.Data.toEntity(): List<AnimeListItem> {
    return animes.map { it.animeFields.toEntity() }
}