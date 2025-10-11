package com.sharapov.core_data.local.dbmodel.anime_details

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sharapov.core_data.local.dbmodel.anime.AnimeDbModel

@Entity(
    tableName = "alternative_titles",
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
data class AlternativeTitlesDbModel(
    @PrimaryKey val animeId: Int,
    @ColumnInfo(defaultValue = "") val en: String = "",
    @ColumnInfo(defaultValue = "") val ja: String = ""
)

@Entity(
    tableName = "alternative_title_synonyms",
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
        Index(value = ["animeId", "value"], unique = true)
    ]
)
data class AlternativeTitleSynonymDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val animeId: Int,
    @ColumnInfo(defaultValue = "") val value: String = ""
)