package com.sharapov.core_data.remote.dto.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusSoloDto(
    @SerialName("completed")
    val completed: String = "",
    @SerialName("dropped")
    val dropped: String = "",
    @SerialName("on_hold")
    val onHold: String = "",
    @SerialName("plan_to_watch")
    val planToWatch: String = "",
    @SerialName("watching")
    val watching: String = ""
)