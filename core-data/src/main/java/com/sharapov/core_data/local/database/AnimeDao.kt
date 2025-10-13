package com.sharapov.core_data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.sharapov.core_data.local.dbmodel.AnimeWithDetailsDbModel
import com.sharapov.core_data.local.dbmodel.anime.AnimeDbModel
import com.sharapov.core_data.local.dbmodel.anime.AnimeListItemDbModel
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
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Transaction
    @Query(
        """
        SELECT a.* FROM anime a
        JOIN anime_ranking_type art ON art.animeId = a.id
        JOIN ranking_type rt ON rt.id = art.rankingTypeId
        WHERE rt.name = :rankingType
        ORDER BY a.rating DESC
    """
    )
    fun getAnimeListForRankingType(rankingType: String): Flow<List<AnimeListItemDbModel>>

    @Transaction
    @Query(
        """
    SELECT * 
    FROM anime 
    ORDER BY rating DESC
    """
    )
    fun getAnimeList(): Flow<List<AnimeListItemDbModel>>

    @Transaction
    @Query(
        """
    SELECT a.* FROM anime a
    JOIN anime_genre_cross_ref genre ON genre.animeId = a.id
    JOIN genre g ON g.id = genre.genreId
    WHERE g.name = :genre
    ORDER BY rating DESC
    """
    )
    fun getAnimeListForGenre(genre: String): Flow<List<AnimeListItemDbModel>>

    @Upsert
    suspend fun upsertAnime(animeList: List<AnimeDbModel>)

    @Upsert
    suspend fun upsertGenres(genres: List<GenreDbModel>)

    @Upsert
    suspend fun upsertStudios(studios: List<StudioDbModel>)

    @Upsert
    suspend fun upsertRankingTypes(rankingTypes: List<RankingTypeDbModel>)

    @Query("SELECT * FROM ranking_type WHERE name IN (:names)")
    suspend fun getRankingTypesByNames(names: List<String>): List<RankingTypeDbModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRankingTypes(types: List<AnimeRankingTypeCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnimeGenreRefs(refs: List<AnimeGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnimeStudioRefs(refs: List<AnimeStudioCrossRef>)

    @Transaction
    suspend fun upsertFullAnime(animeFullDbModelList: List<AnimeListItemDbModel>) {
        upsertAnime(animeFullDbModelList.map { it.anime })
        upsertGenres(animeFullDbModelList.flatMap { it.genres }.distinctBy { it.id })
        upsertStudios(animeFullDbModelList.flatMap { it.studios }.distinctBy { it.id })

        val typesByName = animeFullDbModelList
            .flatMap { it.rankingTypes }
            .distinctBy { it.name }
        upsertRankingTypes(typesByName)

        val genreRefs = animeFullDbModelList.flatMap { full ->
            full.genres.map { g -> AnimeGenreCrossRef(animeId = full.anime.id, genreId = g.id) }
        }
        val studioRefs = animeFullDbModelList.flatMap { full ->
            full.studios.map { s -> AnimeStudioCrossRef(animeId = full.anime.id, studioId = s.id) }
        }
        val typesFromDb = getRankingTypesByNames(typesByName.map { it.name })
        val typeIdByName = typesFromDb.associateBy({ it.name }, { it.id })
        val rankingTypeRefs = animeFullDbModelList.flatMap { full ->
            full.rankingTypes.mapNotNull { r ->
                val rtId = typeIdByName[r.name] ?: return@mapNotNull null
                AnimeRankingTypeCrossRef(animeId = full.anime.id, rankingTypeId = rtId)
            }
        }
        if (genreRefs.isNotEmpty()) insertAnimeGenreRefs(genreRefs)
        if (studioRefs.isNotEmpty()) insertAnimeStudioRefs(studioRefs)
        if (rankingTypeRefs.isNotEmpty()) insertRankingTypes(rankingTypeRefs)
    }

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

    @Query("SELECT * FROM anime_statistics WHERE animeId = :animeId")
    suspend fun getStatistics(animeId: Int): StatisticsDbModel

    @Upsert
    suspend fun upsertStartSeasons(items: List<StartSeasonDbModel>)

    @Query("SELECT * FROM anime_start_season WHERE animeId = :animeId")
    suspend fun getStartSeason(animeId: Int): StartSeasonDbModel

    @Upsert
    suspend fun upsertAlternativeTitles(items: List<AlternativeTitlesDbModel>)

    @Query("SELECT * FROM alternative_titles WHERE animeId = :animeId")
    suspend fun getAlternativeTitles(animeId: Int): AlternativeTitlesDbModel


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlternativeTitleSynonyms(items: List<AlternativeTitleSynonymDbModel>)

    @Query("DELETE FROM alternative_title_synonyms WHERE animeId = :animeId")
    suspend fun clearAlternativeTitleSynonyms(animeId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecommendations(items: List<RecommendationsDbModel>)

    @Query("DELETE FROM anime_recommendations WHERE animeId = :animeId")
    suspend fun clearRecommendations(animeId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRelatedAnime(items: List<RelatedAnimeDbModel>)

    @Query("DELETE FROM anime_related WHERE animeId = :animeId")
    suspend fun clearRelatedAnime(animeId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPictures(items: List<PictureDbModel>)

    @Query("DELETE FROM anime_pictures WHERE animeId = :animeId")
    suspend fun clearPictures(animeId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnimeIgnore(items: List<AnimeDbModel>)

    @Query("DELETE FROM anime_genre_cross_ref WHERE animeId = :animeId")
    suspend fun clearAnimeGenreRefsForAnime(animeId: Int)

    @Transaction
    suspend fun upsertDetailsBundle(
        details: AnimeDetailsDbModel,
        statistics: StatisticsDbModel,
        startSeason: StartSeasonDbModel,
        alternativeTitles: AlternativeTitlesDbModel,
        synonyms: List<AlternativeTitleSynonymDbModel>,
        recommendedAnime: List<AnimeDbModel>,
        recommendationsLinks: List<RecommendationsDbModel>,
        relatedAnime: List<AnimeDbModel>,
        relatedLinks: List<RelatedAnimeDbModel>,
        pictures: List<PictureDbModel>,
        genres: List<GenreDbModel>
    ) {

        val recIds = recommendationsLinks.map { it.recommendedAnimeId }
        val relIds = relatedLinks.map { it.relatedAnimeId }
        val allTargetIds = (recIds + relIds).distinct()

        if (allTargetIds.isNotEmpty()) {
            val placeholders = allTargetIds.map { id ->
                AnimeDbModel(
                    id = id,
                    title = "",
                    imageUrl = "",
                    rating = 0.0,
                    createdAt = "",
                    isFavorite = false
                )
            }
            insertAnimeIgnore(placeholders)
        }

        val realBase = (recommendedAnime + relatedAnime).distinctBy { it.id }
        if (realBase.isNotEmpty()) upsertAnime(realBase)

        upsertAnimeDetails(listOf(details))
        upsertStatistics(listOf(statistics))
        upsertStartSeasons(listOf(startSeason))
        upsertAlternativeTitles(listOf(alternativeTitles))

        if (genres.isNotEmpty()) {
            upsertGenres(genres.distinctBy { it.id })
            clearAnimeGenreRefsForAnime(details.id)
            val genreRefs = genres.map { g -> AnimeGenreCrossRef(animeId = details.id, genreId = g.id) }
            insertAnimeGenreRefs(genreRefs)
        }
        
        clearAlternativeTitleSynonyms(details.id)
        if (synonyms.isNotEmpty()) insertAlternativeTitleSynonyms(synonyms)

        clearRecommendations(details.id)
        if (recommendationsLinks.isNotEmpty()) insertRecommendations(recommendationsLinks)

        clearRelatedAnime(details.id)
        if (relatedLinks.isNotEmpty()) insertRelatedAnime(relatedLinks)

        clearPictures(details.id)
        if (pictures.isNotEmpty()) insertPictures(pictures)
    }

    @Query(
        """
    SELECT DISTINCT a.* FROM anime a
    LEFT JOIN alternative_titles altT ON a.id = altT.animeId
    LEFT JOIN alternative_title_synonyms syn ON altT.animeId = syn.animeId
    WHERE a.title LIKE '%' || :query || '%' OR altT.en LIKE '%' || :query || '%'
       OR altT.ja LIKE '%' || :query || '%'
       OR syn.value LIKE '%' || :query || '%'
    """
    )
    suspend fun searchAnime(query: String): List<AnimeDbModel>

    @Query("SELECT EXISTS(SELECT 1 FROM anime_details WHERE id = :id)")
    suspend fun hasDetails(id: Int): Boolean

    @Query("UPDATE anime SET isFavorite = NOT isFavorite WHERE id = :animeId")
    suspend fun changeAnimeFavoriteStatus(animeId: Int)
}
