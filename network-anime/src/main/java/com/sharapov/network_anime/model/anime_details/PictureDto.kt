package com.sharapov.network_anime.model.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PictureDto(
    @SerialName("large")
    val large: String? = "",
    @SerialName("medium")
    val medium: String? = ""
)