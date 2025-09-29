package com.sharapov.core_data.local.dbmodel

import androidx.room.PrimaryKey

data class AnimeDbModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Double,
    val rank: Int,
    val genres: List<GenreDbModel>,
    val createdAt: String, // "2022-09-09T10:01:30+00:00"
    val studios: List<StudioDbModel>
)
