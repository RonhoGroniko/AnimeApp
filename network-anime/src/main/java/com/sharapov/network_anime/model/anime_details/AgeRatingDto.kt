package com.sharapov.network_anime.model.anime_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AgeRatingDto {
    @SerialName("g")
    G,

    @SerialName("pg")
    PG,

    @SerialName("pg_13")
    PG_13,

    @SerialName("r")
    R,

    @SerialName("r+")
    R_PLUS,

    @SerialName("rx")
    RX,

    @SerialName("unknown")
    UNKNOWN
}