package com.sharapov.network_anime.model.anime


import com.sharapov.network_anime.model.common.GenreDto
import com.sharapov.network_anime.model.common.MainPictureDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeResponseDto(
    @SerialName("data")
    val `data`: List<DataDto> = listOf(),
    @SerialName("paging")
    val paging: PagingDto = PagingDto()
)

@Serializable
data class DataDto(
    @SerialName("node")
    val node: NodeDto = NodeDto()
)

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

@Serializable
data class StudioDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = ""
)

@Serializable
data class PagingDto(
    @SerialName("next")
    val next: String = ""
)