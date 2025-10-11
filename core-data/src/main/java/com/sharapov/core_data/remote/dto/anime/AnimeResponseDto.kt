package com.sharapov.core_data.remote.dto.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeResponseDto(
    @SerialName("data")
    val `data`: List<DataDto> = listOf(),
    @SerialName("paging")
    val paging: PagingDto = PagingDto()
)