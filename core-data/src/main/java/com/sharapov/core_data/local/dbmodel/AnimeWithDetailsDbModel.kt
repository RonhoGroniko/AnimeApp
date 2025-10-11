package com.sharapov.core_data.local.dbmodel

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.sharapov.core_data.local.dbmodel.anime.AnimeDbModel
import com.sharapov.core_data.local.dbmodel.anime.AnimeGenreCrossRef
import com.sharapov.core_data.local.dbmodel.anime.AnimeRankingTypeCrossRef
import com.sharapov.core_data.local.dbmodel.anime.AnimeStudioCrossRef
import com.sharapov.core_data.local.dbmodel.anime.GenreDbModel
import com.sharapov.core_data.local.dbmodel.anime.RankingTypeDbModel
import com.sharapov.core_data.local.dbmodel.anime.StudioDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.AlternativeTitleSynonymDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.AlternativeTitlesDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.AnimeDetailsDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.PictureDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.RecommendationsDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.RelatedAnimeDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.StartSeasonDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.StatisticsDbModel

data class AnimeWithDetailsDbModel(
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
    val rankingTypes: List<RankingTypeDbModel>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val details: AnimeDetailsDbModel?,


    @Relation(
        parentColumn = "id",
        entityColumn = "animeId"
    )
    val statistics: StatisticsDbModel?,


    @Relation(
        parentColumn = "id",
        entityColumn = "animeId"
    )
    val startSeason: StartSeasonDbModel?,


    @Relation(
        parentColumn = "id",
        entityColumn = "animeId",
        entity = AlternativeTitlesDbModel::class
    )
    val alternativeTitles: AlternativeTitlesWithSynonymsDbModel?,


    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = RecommendationsDbModel::class,
            parentColumn = "animeId",
            entityColumn = "recommendedAnimeId"
        )
    )
    val recommendations: List<AnimeDbModel>,

    @Relation(
        parentColumn = "id",
        entityColumn = "animeId",
        entity = RelatedAnimeDbModel::class
    )
    val relatedAnime: List<RelatedWithAnimeDbModel>,

    @Relation(
        parentColumn = "id",
        entityColumn = "animeId",
        entity = PictureDbModel::class
    )
    val pictures: List<PictureDbModel>
)


data class AlternativeTitlesWithSynonymsDbModel(
    @Embedded val titles: AlternativeTitlesDbModel,
    @Relation(
        parentColumn = "animeId",
        entityColumn = "animeId",
        entity = AlternativeTitleSynonymDbModel::class
    )
    val synonyms: List<AlternativeTitleSynonymDbModel>
)

data class RelatedWithAnimeDbModel(
    @Embedded val link: RelatedAnimeDbModel,
    @Relation(
        parentColumn = "relatedAnimeId",
        entityColumn = "id"
    )
    val anime: AnimeDbModel
)