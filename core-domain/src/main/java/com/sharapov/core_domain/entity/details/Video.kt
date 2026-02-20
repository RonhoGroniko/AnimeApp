package com.sharapov.core_domain.entity.details

data class Video(
    val id: Long,
    val name: String,
    val kind: VideoKind,
    val url: String,
    val imageUrl: String
)

enum class VideoKind(val value: String) {
    PV("PV"),
    CHARACTER_TRAILER("Character trailer"),
    CM("CM"),
    OP("OP"),
    ED("ED"),
    OP_ED_CLIP("Music"),
    CLIP("Clip"),
    OTHER("Other"),
    EPISODE_PREVIEW("Episode preview"),
    UNKNOWN("Unknown"),
}