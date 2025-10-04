package com.sharapov.core_data.remote.dto.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationSoloDto(
    @SerialName("node")
    val node: NodeSoloDto = NodeSoloDto(),
    @SerialName("num_recommendations")
    val numRecommendations: Int = 0
)