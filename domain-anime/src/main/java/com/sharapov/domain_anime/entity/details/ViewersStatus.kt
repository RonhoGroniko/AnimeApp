package com.sharapov.domain_anime.entity.details

data class ViewersStatus(
    val completed: Int,
    val dropped: Int,
    val onHold: Int,
    val planToWatch: Int,
    val watching: Int
)
