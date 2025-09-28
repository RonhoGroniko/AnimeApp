package com.sharapov.core_domain.entity

data class Anime(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Double,
    val rank: Int,
    val genres: List<Genre>,
    val createdAt: String, // "2022-09-09T10:01:30+00:00"
    val studios: List<Studio>
)
