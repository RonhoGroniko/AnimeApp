package com.sharapov.database_anime

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sharapov.database_anime.dao.AnimeCoreDao
import com.sharapov.database_anime.dao.AnimeDetailsDao
import com.sharapov.database_anime.dao.AnimeListDao
import com.sharapov.database_anime.dao.AnimeSearchDao
import com.sharapov.database_anime.model.details.AlternativeTitleSynonymDbModel
import com.sharapov.database_anime.model.details.AlternativeTitlesDbModel
import com.sharapov.database_anime.model.details.AnimeDetailsDbModel
import com.sharapov.database_anime.model.details.PictureDbModel
import com.sharapov.database_anime.model.details.RecommendationsDbModel
import com.sharapov.database_anime.model.details.RelatedAnimeDbModel
import com.sharapov.database_anime.model.details.StartSeasonDbModel
import com.sharapov.database_anime.model.details.StatisticsDbModel
import com.sharapov.database_anime.model.details.converters.AgeRatingConverter
import com.sharapov.database_anime.model.details.converters.MediaTypeConverter
import com.sharapov.database_anime.model.details.converters.RelationTypeConverter
import com.sharapov.database_anime.model.details.converters.SourceTypeConverter
import com.sharapov.database_anime.model.details.converters.StatusTypeConverter
import com.sharapov.database_anime.model.list.AnimeDbModel
import com.sharapov.database_anime.model.list.AnimeGenreCrossRef
import com.sharapov.database_anime.model.list.AnimeRankingTypeCrossRef
import com.sharapov.database_anime.model.list.AnimeStudioCrossRef
import com.sharapov.database_anime.model.list.GenreDbModel
import com.sharapov.database_anime.model.list.RankingTypeDbModel
import com.sharapov.database_anime.model.list.StudioDbModel

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
    version = 11,
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

    abstract fun animeListDao(): AnimeListDao

    abstract fun animeCoreDao(): AnimeCoreDao

    abstract fun animeSearchDao(): AnimeSearchDao

    abstract fun animeDetailsDao(): AnimeDetailsDao
}