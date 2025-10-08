package com.sharapov.core_data.remote.dto.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelatedAnimeDto(
    @SerialName("node")
    val node: NodeDetailsDto = NodeDetailsDto(),
    @SerialName("relation_type")
    val relationType: RelationTypeDto = RelationTypeDto.UNKNOWN,
    @SerialName("relation_type_formatted")
    val relationTypeFormatted: String = ""
)