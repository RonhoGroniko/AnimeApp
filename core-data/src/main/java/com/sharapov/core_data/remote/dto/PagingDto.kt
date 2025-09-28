package com.sharapov.core_data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingDto(
    @SerialName("next")
    val next: String = ""
)