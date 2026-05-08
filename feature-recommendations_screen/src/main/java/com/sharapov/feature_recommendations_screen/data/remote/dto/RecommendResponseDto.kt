package com.sharapov.feature_recommendations_screen.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendResponseDto(
    @SerialName("items")
    val recommendedAnimeDtos: List<RecommendedAnimeDto> = listOf()
)