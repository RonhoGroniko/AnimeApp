package com.sharapov.core_data.remote.dto.anime_details


import com.sharapov.core_data.remote.serializers.FlexibleIntNullableSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusSoloDto(
    @SerialName("completed")
    @Serializable(with = FlexibleIntNullableSerializer::class)
    val completed: Int? = 0,
    @SerialName("dropped")
    @Serializable(with = FlexibleIntNullableSerializer::class)
    val dropped: Int? = 0,
    @SerialName("on_hold")
    @Serializable(with = FlexibleIntNullableSerializer::class)
    val onHold: Int? = 0,
    @SerialName("plan_to_watch")
    @Serializable(with = FlexibleIntNullableSerializer::class)
    val planToWatch: Int? = 0,
    @SerialName("watching")
    @Serializable(with = FlexibleIntNullableSerializer::class)
    val watching: Int? = 0
)