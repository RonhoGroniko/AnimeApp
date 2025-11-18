package com.sharapov.network_anime.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = ""
)


@Serializable
data class MainPictureDto(
    @SerialName("large")
    val large: String? = "",
    @SerialName("medium")
    val medium: String? = ""
)
