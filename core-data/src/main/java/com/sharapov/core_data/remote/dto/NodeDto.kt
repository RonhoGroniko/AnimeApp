package com.sharapov.core_data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NodeDto(
    @SerialName("created_at")
    val createdAt: String = "",
    @SerialName("genres")
    val genres: List<GenreDto> = listOf(),
    @SerialName("id")
    val id: Int = 0,
    @SerialName("main_picture")
    val mainPicture: MainPictureDto = MainPictureDto(),
    @SerialName("mean")
    val mean: Double = 0.0,
    @SerialName("rank")
    val rank: Int = 0,
    @SerialName("studios")
    val studios: List<StudioDto> = listOf(),
    @SerialName("title")
    val title: String = ""
)