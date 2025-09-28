package com.sharapov.core_data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = ""
)