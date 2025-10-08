package com.sharapov.core_data.remote.dto.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatisticsDto(
    @SerialName("num_list_users")
    val numListUsers: Int = 0,
    @SerialName("status")
    val status: StatusDetailsDto = StatusDetailsDto()
)