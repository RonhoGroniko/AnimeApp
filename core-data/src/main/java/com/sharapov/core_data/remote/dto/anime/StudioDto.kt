package com.sharapov.core_data.remote.dto.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudioDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = ""
)