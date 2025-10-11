package com.sharapov.core_data.remote.dto.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingDto(
    @SerialName("next")
    val next: String = ""
)