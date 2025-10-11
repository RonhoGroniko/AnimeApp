package com.sharapov.core_data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
import com.sharapov.core_data.local.dbmodel.anime_details.converters.AgeRatingConverter
import com.sharapov.core_data.local.dbmodel.anime_details.converters.MediaTypeConverter
import com.sharapov.core_data.local.dbmodel.anime_details.converters.RelationTypeConverter
import com.sharapov.core_data.local.dbmodel.anime_details.converters.SourceTypeConverter
import com.sharapov.core_data.local.dbmodel.anime_details.converters.StatusTypeConverter

@Database(
    entities = [
        AnimeDbModel::class,
        GenreDbModel::class,
        StudioDbModel::class,
        RankingTypeDbModel::class,
        AnimeGenreCrossRef::class,
        AnimeStudioCrossRef::class,
        AnimeRankingTypeCrossRef::class,
        AnimeDetailsDbModel::class,
        AlternativeTitlesDbModel::class,
        AlternativeTitleSynonymDbModel::class,
        RecommendationsDbModel::class,
        RelatedAnimeDbModel::class,
        StartSeasonDbModel::class,
        StatisticsDbModel::class,
        PictureDbModel::class
    ],
    version = 8,
    exportSchema = false
)
@TypeConverters(
    MediaTypeConverter::class,
    AgeRatingConverter::class,
    StatusTypeConverter::class,
    SourceTypeConverter::class,
    RelationTypeConverter::class
)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao
}