package com.sharapov.database_anime.model.details

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sharapov.database_anime.model.list.AnimeDbModel

@Entity(
    tableName = "anime_statistics",
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
data class StatisticsDbModel(
    @PrimaryKey
    val animeId: Int,

    @ColumnInfo(defaultValue = "0")
    val completed: Int = 0,

    @ColumnInfo(defaultValue = "0")
    val dropped: Int = 0,

    @ColumnInfo(defaultValue = "0")
    val onHold: Int = 0,

    @ColumnInfo(defaultValue = "0")
    val planToWatch: Int = 0,

    @ColumnInfo(defaultValue = "0")
    val watching: Int = 0
)