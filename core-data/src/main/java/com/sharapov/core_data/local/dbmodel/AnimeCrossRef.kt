package com.sharapov.core_data.local.dbmodel

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "anime_genre_cross_ref",
    primaryKeys = ["animeId", "genreId"],
    foreignKeys = [
        ForeignKey(
            entity = AnimeDbModel::class,
            parentColumns = ["id"],
            childColumns = ["animeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenreDbModel::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("animeId"), Index("genreId")]
)
data class AnimeGenreCrossRef(
    val animeId: Int,
    val genreId: Int
)

@Entity(
    tableName = "anime_studio_cross_ref",
    primaryKeys = ["animeId", "studioId"],
    foreignKeys = [
        ForeignKey(
            entity = AnimeDbModel::class,
            parentColumns = ["id"],
            childColumns = ["animeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = StudioDbModel::class,
            parentColumns = ["id"],
            childColumns = ["studioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("animeId"), Index("studioId")]
)
data class AnimeStudioCrossRef(
    val animeId: Int,
    val studioId: Int
)