package com.sharapov.core_data.remote.dto.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PictureSoloDto(
    @SerialName("large")
    val large: String = "",
    @SerialName("medium")
    val medium: String = ""
)