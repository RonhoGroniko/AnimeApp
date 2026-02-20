package com.sharapov.feature_details_screen.domain.entity

data class Character(
    val roles: List<String>,
    val id: Long,
    val name: String,
    val imageUrl: String
)