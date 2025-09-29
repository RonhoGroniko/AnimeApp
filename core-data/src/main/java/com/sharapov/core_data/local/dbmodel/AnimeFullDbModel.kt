package com.sharapov.core_data.local.dbmodel

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AnimeFullDbModel(
    @Embedded val anime: AnimeDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(AnimeGenreCrossRef::class)
    )
    val genres: List<GenreDbModel>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(AnimeStudioCrossRef::class)
    )
    val studios: List<StudioDbModel>
)
