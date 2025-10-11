package com.sharapov.core_data.local.dbmodel.anime

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AnimeListItemDbModel(
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
    val studios: List<StudioDbModel>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = AnimeRankingTypeCrossRef::class,
            parentColumn = "animeId",
            entityColumn = "rankingTypeId"
        )
    )
    val rankingTypes: List<RankingTypeDbModel>
)
