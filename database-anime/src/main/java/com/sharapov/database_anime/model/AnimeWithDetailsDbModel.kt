package com.sharapov.database_anime.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.sharapov.database_anime.model.details.AlternativeTitleSynonymDbModel
import com.sharapov.database_anime.model.details.AlternativeTitlesDbModel
import com.sharapov.database_anime.model.details.AnimeDetailsDbModel
import com.sharapov.database_anime.model.details.PictureDbModel
import com.sharapov.database_anime.model.details.RecommendationsDbModel
import com.sharapov.database_anime.model.details.RelatedAnimeDbModel
import com.sharapov.database_anime.model.details.StartSeasonDbModel
import com.sharapov.database_anime.model.details.StatisticsDbModel
import com.sharapov.database_anime.model.list.AnimeDbModel
import com.sharapov.database_anime.model.list.AnimeGenreCrossRef
import com.sharapov.database_anime.model.list.AnimeRankingTypeCrossRef
import com.sharapov.database_anime.model.list.AnimeStudioCrossRef
import com.sharapov.database_anime.model.list.GenreDbModel
import com.sharapov.database_anime.model.list.RankingTypeDbModel
import com.sharapov.database_anime.model.list.StudioDbModel

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