package com.sharapov.network_anime.model.anime


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeResponseDto(
    @SerialName("data")
    val `data`: List<DataDto> = listOf(),
    @SerialName("paging")
    val paging: PagingDto = PagingDto()
)