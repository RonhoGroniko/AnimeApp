package com.sharapov.core_data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataDto(
    @SerialName("node")
    val node: NodeDto = NodeDto(),
    @SerialName("ranking")
    val ranking: RankingDto = RankingDto()
)