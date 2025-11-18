package com.sharapov.network_anime.model.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainPictureDto(
    @SerialName("large")
    val large: String? = "",
    @SerialName("medium")
    val medium: String? = ""
)