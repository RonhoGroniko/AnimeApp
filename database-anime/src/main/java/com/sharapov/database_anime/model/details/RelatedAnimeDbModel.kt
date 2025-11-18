package com.sharapov.database_anime.model.details

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.sharapov.domain_anime.entity.details.RelationType
import com.sharapov.database_anime.model.list.AnimeDbModel

@Entity(
    tableName = "anime_related",
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
            childColumns = ["relatedAnimeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["animeId"]),
        Index(value = ["relatedAnimeId"])
    ],
    primaryKeys = ["animeId", "relatedAnimeId"]
)
data class RelatedAnimeDbModel(
    val animeId: Int,
    val relatedAnimeId: Int,
    val relation: RelationType
)
