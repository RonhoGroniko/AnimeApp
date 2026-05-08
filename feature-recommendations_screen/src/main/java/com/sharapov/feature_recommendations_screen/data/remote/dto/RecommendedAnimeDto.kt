package com.sharapov.feature_recommendations_screen.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedAnimeDto(
    @SerialName("anime_id")
    val animeId: Long = 0,
    @SerialName("collaborative_rank")
    val collaborativeRank: Int? = 0,
    @SerialName("content_rank")
    val contentRank: Int = 0,
    @SerialName("image_url")
    val imageUrl: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("score")
    val score: Double = 0.0
)