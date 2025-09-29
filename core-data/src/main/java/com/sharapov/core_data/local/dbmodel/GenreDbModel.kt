package com.sharapov.core_data.local.dbmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreDbModel(
    @PrimaryKey
    val id: Int,
    val name: String
)