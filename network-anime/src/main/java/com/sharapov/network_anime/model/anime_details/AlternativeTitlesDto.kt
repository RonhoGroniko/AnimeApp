package com.sharapov.network_anime.model.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlternativeTitlesDto(
    @SerialName("en")
    val en: String = "",
    @SerialName("ja")
    val ja: String = "",
    @SerialName("synonyms")
    val synonyms: List<String> = listOf()
)