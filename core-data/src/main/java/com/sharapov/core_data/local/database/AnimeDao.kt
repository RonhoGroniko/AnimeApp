package com.sharapov.core_data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.sharapov.core_data.local.dbmodel.AnimeDbModel
import com.sharapov.core_data.local.dbmodel.AnimeFullDbModel
import com.sharapov.core_data.local.dbmodel.AnimeGenreCrossRef
import com.sharapov.core_data.local.dbmodel.AnimeRankingTypeCrossRef
import com.sharapov.core_data.local.dbmodel.AnimeStudioCrossRef
import com.sharapov.core_data.local.dbmodel.GenreDbModel
import com.sharapov.core_data.local.dbmodel.RankingTypeDbModel
import com.sharapov.core_data.local.dbmodel.StudioDbModel
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
    fun getAnimeList(rankingType: String): Flow<List<AnimeFullDbModel>>

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
    suspend fun upsertFullAnime(animeFullDbModelList: List<AnimeFullDbModel>) {
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
}