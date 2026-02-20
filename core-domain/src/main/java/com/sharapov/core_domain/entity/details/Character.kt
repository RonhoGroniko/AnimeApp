package com.sharapov.core_domain.entity.details

data class Character(
    val roles: List<String>,
    val id: Long,
    val name: String,
    val imageUrl: String
)