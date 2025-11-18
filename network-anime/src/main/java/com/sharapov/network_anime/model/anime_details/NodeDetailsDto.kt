package com.sharapov.network_anime.model.anime_details


import com.sharapov.network_anime.model.anime.MainPictureDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NodeDetailsDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("main_picture")
    val mainPicture: MainPictureDto = MainPictureDto(),
    @SerialName("title")
    val title: String = ""
)