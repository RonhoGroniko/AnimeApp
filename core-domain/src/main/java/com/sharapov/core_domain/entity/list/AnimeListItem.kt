package com.sharapov.core_domain.entity.list

data class AnimeListItem(
    val id: Long,
    val name: String,
    val score: Double,
    val imageUrl: String
)