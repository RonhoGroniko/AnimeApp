package com.sharapov.core_domain.entity.details

data class ViewersStatus(
    val completed: Int,
    val dropped: Int,
    val onHold: Int,
    val planToWatch: Int,
    val watching: Int
)
