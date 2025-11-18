package com.sharapov.network_anime.model.anime_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RelationTypeDto {
    @SerialName("sequel")
    SEQUEL,

    @SerialName("prequel")
    PREQUEL,

    @SerialName("alternative_setting")
    ALTERNATIVE_SETTING,

    @SerialName("alternative_version")
    ALTERNATIVE_VERSION,

    @SerialName("side_story")
    SIDE_STORY,

    @SerialName("parent_story")
    PARENT_STORY,

    @SerialName("summary")
    SUMMARY,

    @SerialName("full_story")
    FULL_STORY,

    @SerialName("spinoff")
    SPINOFF,

    @SerialName("adaptation")
    ADAPTATION,

    @SerialName("character")
    CHARACTER,

    @SerialName("other")
    OTHER,

    @SerialName("unknown")
    UNKNOWN
}