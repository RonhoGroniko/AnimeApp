package com.sharapov.feature_recommendations_screen.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendRequestDto(
    @SerialName("anime_ids")
    val animeIds: List<Int>,

    @SerialName("limit")
    val limit: Int = 40
)
