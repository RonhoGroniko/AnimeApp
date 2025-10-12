package com.sharapov.core_data.local.dbmodel.anime_details

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sharapov.core_data.local.dbmodel.anime.AnimeDbModel
import com.sharapov.core_domain.entity.details.AgeRating
import com.sharapov.core_domain.entity.details.MediaType
import com.sharapov.core_domain.entity.details.Source
import com.sharapov.core_domain.entity.details.Status

@Entity(
    tableName = "anime_details",
    foreignKeys = [
        ForeignKey(
            entity = AnimeDbModel::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["id"], unique = true)
    ]
)
data class AnimeDetailsDbModel(
    @PrimaryKey
    val id: Int,
    val averageEpisodeDuration: Int,
    val background: String,
    val endDate: String,
    val mediaType: MediaType,
    val nsfw: String,
    val numEpisodes: Int,
    val numListUsers: Int,
    val numScoringUsers: Int,
    val popularity: Int,
    val rank: Int,
    val mean: Double,
    val rating: AgeRating,
    val source: Source,
    val startDate: String,
    val status: Status,
    val synopsis: String,
    val updatedAt: String
)
