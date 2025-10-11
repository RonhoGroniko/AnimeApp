package com.sharapov.core_data.local.dbmodel.anime

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ranking_type",
    indices = [Index(value = ["name"], unique = true)]
)
data class RankingTypeDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
