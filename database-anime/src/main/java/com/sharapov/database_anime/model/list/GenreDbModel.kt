package com.sharapov.database_anime.model.list

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreDbModel(
    @PrimaryKey
    val id: Int,
    val name: String
)