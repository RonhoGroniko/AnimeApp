package com.sharapov.core_data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankingDto(
    @SerialName("rank")
    val rank: Int = 0
)