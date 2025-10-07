package com.sharapov.core_data.remote.dto.anime_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MediaTypeDto {
    @SerialName("tv")
    TV,

    @SerialName("ova")
    OVA,

    @SerialName("movie")
    MOVIE,

    @SerialName("special")
    SPECIAL,

    @SerialName("ona")
    ONA,

    @SerialName("music")
    MUSIC,

    @SerialName("unknown")
    UNKNOWN
}