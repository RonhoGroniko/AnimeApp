package com.sharapov.core_data.remote.dto.anime_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelatedAnimeSoloDto(
    @SerialName("node")
    val node: NodeSoloDto = NodeSoloDto(),
    @SerialName("relation_type")
    val relationType: RelationTypeDto = RelationTypeDto.UNKNOWN,
    @SerialName("relation_type_formatted")
    val relationTypeFormatted: String = ""
)