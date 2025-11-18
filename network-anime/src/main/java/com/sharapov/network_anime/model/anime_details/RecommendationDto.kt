package com.sharapov.network_anime.model.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationDto(
    @SerialName("node")
    val node: NodeDetailsDto = NodeDetailsDto(),
    @SerialName("num_recommendations")
    val numRecommendations: Int = 0
)