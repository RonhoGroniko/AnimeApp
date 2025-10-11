package com.sharapov.core_data.local.dbmodel.anime_details

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.sharapov.core_data.local.dbmodel.anime.AnimeDbModel

@Entity(
    tableName = "anime_recommendations",
    foreignKeys = [
        ForeignKey(
            entity = AnimeDbModel::class,
            parentColumns = ["id"],
            childColumns = ["animeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AnimeDbModel::class,
            parentColumns = ["id"],
            childColumns = ["recommendedAnimeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["animeId"]),
        Index(value = ["recommendedAnimeId"])
    ],
    primaryKeys = ["animeId", "recommendedAnimeId"]
)
data class RecommendationsDbModel(
    val animeId: Int,
    val recommendedAnimeId: Int
)
