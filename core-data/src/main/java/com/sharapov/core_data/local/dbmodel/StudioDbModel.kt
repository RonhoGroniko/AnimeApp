package com.sharapov.core_data.local.dbmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studio")
data class StudioDbModel(
    @PrimaryKey
    val id: Int,
    val name: String
)
