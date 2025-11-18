package com.sharapov.network_anime.model.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingDto(
    @SerialName("next")
    val next: String = ""
)