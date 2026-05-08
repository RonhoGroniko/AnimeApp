package com.sharapov.feature_recommendations_screen.data.mapper

import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.feature_recommendations_screen.data.remote.dto.RecommendResponseDto
import com.sharapov.feature_recommendations_screen.data.remote.dto.RecommendedAnimeDto

fun RecommendResponseDto.toEntity(): List<AnimeListItem> {
    return this.recommendedAnimeDtos.map { it.toEntity() }
}

fun RecommendedAnimeDto.toEntity(): AnimeListItem {
    return AnimeListItem(
        id = animeId,
        name = name,
        score = score,
        imageUrl = imageUrl
    )
}