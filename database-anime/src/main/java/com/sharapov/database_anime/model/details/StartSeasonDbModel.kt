package com.sharapov.database_anime.model.details

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sharapov.database_anime.model.list.AnimeDbModel

@Entity(
    tableName = "anime_start_season",
    foreignKeys = [
        ForeignKey(
            entity = AnimeDbModel::class,
            parentColumns = ["id"],
            childColumns = ["animeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["animeId"], unique = true)]
)
data class StartSeasonDbModel(
    @PrimaryKey val animeId: Int,
    val season: String,
    val year: Int
)
