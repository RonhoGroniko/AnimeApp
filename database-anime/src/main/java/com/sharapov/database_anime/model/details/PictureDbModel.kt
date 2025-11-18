package com.sharapov.database_anime.model.details

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sharapov.database_anime.model.list.AnimeDbModel

@Entity(
    tableName = "anime_pictures",
    foreignKeys = [
        ForeignKey(
            entity = AnimeDbModel::class,
            parentColumns = ["id"],
            childColumns = ["animeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["animeId"]),
        Index(value = ["animeId", "url"], unique = true)
    ]
)
data class PictureDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val animeId: Int,
    @ColumnInfo(defaultValue = "") val url: String = ""
)