package com.sharapov.core_data.remote.dto.anime_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class SourceDto {
        @SerialName("original") ORIGINAL,
        @SerialName("manga") MANGA,
        @SerialName("4_koma_manga") FOUR_KOMA_MANGA, // Манга в формате 4-кома (четыре кадра, юмор)
        @SerialName("web_manga") WEB_MANGA,
        @SerialName("digital_manga") DIGITAL_MANGA,
        @SerialName("novel") NOVEL,
        @SerialName("light_novel") LIGHT_NOVEL,
        @SerialName("visual_novel") VISUAL_NOVEL,
        @SerialName("game") GAME,
        @SerialName("card_game") CARD_GAME,
        @SerialName("book") BOOK,
        @SerialName("picture_book") PICTURE_BOOK,
        @SerialName("other") OTHER,
        @SerialName("unknown") UNKNOWN
}