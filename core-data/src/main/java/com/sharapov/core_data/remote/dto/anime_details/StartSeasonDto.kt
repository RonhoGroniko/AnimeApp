package com.sharapov.core_data.remote.dto.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartSeasonDto(
    @SerialName("season")
    val season: String = "",
    @SerialName("year")
    val year: Int = 0
)