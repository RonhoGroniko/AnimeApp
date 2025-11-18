package com.sharapov.network_anime.model.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataDto(
    @SerialName("node")
    val node: NodeDto = NodeDto()
)