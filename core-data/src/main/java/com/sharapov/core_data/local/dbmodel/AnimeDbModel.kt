package com.sharapov.core_data.local.dbmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeDbModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val imageUrl: String,
    val rating: Double,
    val createdAt: String, // "2022-09-09T10:01:30+00:00"
)
