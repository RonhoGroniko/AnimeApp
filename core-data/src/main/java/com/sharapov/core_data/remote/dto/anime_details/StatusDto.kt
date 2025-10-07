package com.sharapov.core_data.remote.dto.anime_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class StatusDto {
    @SerialName("finished_airing")
    FINISHED,
    @SerialName("currently_airing")
    AIRING,
    @SerialName("not_yet_aired")
    NOT_YET_AIRED,
    @SerialName("unknown")
    UNKNOWN;
}