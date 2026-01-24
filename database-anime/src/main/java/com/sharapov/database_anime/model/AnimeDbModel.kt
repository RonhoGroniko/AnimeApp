package com.sharapov.database_anime.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorites")
data class AnimeDbModel(
    @PrimaryKey
    val id: Long,
    val name: String,
    val score: Double,
    val imageUrl: String,
    val createdAt: Long
)
