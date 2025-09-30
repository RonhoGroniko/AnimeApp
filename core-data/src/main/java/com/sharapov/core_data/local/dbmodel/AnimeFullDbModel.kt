package com.sharapov.core_data.local.dbmodel

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AnimeFullDbModel(
    @Embedded val anime: AnimeDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = AnimeGenreCrossRef::class,
            parentColumn = "animeId",
            entityColumn = "genreId"
        )
    )
    val genres: List<GenreDbModel>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = AnimeStudioCrossRef::class,
            parentColumn = "animeId",
            entityColumn = "studioId"
        )
    )
    val studios: List<StudioDbModel>
)
