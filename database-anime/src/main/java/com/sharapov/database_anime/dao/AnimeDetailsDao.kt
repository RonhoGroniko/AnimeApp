package com.sharapov.database_anime.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.sharapov.database_anime.model.AnimeWithDetailsDbModel
import com.sharapov.database_anime.model.details.AlternativeTitleSynonymDbModel
import com.sharapov.database_anime.model.details.AlternativeTitlesDbModel
import com.sharapov.database_anime.model.details.AnimeDetailsDbModel
import com.sharapov.database_anime.model.details.PictureDbModel
import com.sharapov.database_anime.model.details.RecommendationsDbModel
import com.sharapov.database_anime.model.details.RelatedAnimeDbModel
import com.sharapov.database_anime.model.details.StartSeasonDbModel
import com.sharapov.database_anime.model.details.StatisticsDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDetailsDao {

    @Upsert
    suspend fun upsertAnimeDetails(items: List<AnimeDetailsDbModel>)


    @Transaction
    @Query(
        """
        SELECT a.*
        FROM anime a
        WHERE a.id = :animeId
        """
    )
    fun getAnimeWithDetails(animeId: Int): Flow<AnimeWithDetailsDbModel?>

    @Upsert
    suspend fun upsertStatistics(items: List<StatisticsDbModel>)

    @Upsert
    suspend fun upsertStartSeasons(items: List<StartSeasonDbModel>)

    @Upsert
    suspend fun upsertAlternativeTitles(items: List<AlternativeTitlesDbModel>)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertAlternativeTitleSynonyms(items: List<AlternativeTitleSynonymDbModel>)

    @Query("DELETE FROM alternative_title_synonyms WHERE animeId = :animeId")
    suspend fun clearAlternativeTitleSynonyms(animeId: Int)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertRecommendations(items: List<RecommendationsDbModel>)

    @Query("DELETE FROM anime_recommendations WHERE animeId = :animeId")
    suspend fun clearRecommendations(animeId: Int)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertRelatedAnime(items: List<RelatedAnimeDbModel>)

    @Query("DELETE FROM anime_related WHERE animeId = :animeId")
    suspend fun clearRelatedAnime(animeId: Int)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertPictures(items: List<PictureDbModel>)

    @Query("DELETE FROM anime_pictures WHERE animeId = :animeId")
    suspend fun clearPictures(animeId: Int)


    @Query("DELETE FROM anime_genre_cross_ref WHERE animeId = :animeId")
    suspend fun clearAnimeGenreRefsForAnime(animeId: Int)


    @Query("SELECT EXISTS(SELECT 1 FROM anime_details WHERE id = :id)")
    suspend fun hasDetails(id: Int): Boolean

    @Query("UPDATE anime SET isFavorite = NOT isFavorite WHERE id = :animeId")
    suspend fun changeAnimeFavoriteStatus(animeId: Int)
}
