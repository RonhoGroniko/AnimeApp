package com.sharapov.database_anime.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.sharapov.database_anime.model.list.AnimeDbModel
import com.sharapov.database_anime.model.list.AnimeGenreCrossRef
import com.sharapov.database_anime.model.list.AnimeRankingTypeCrossRef
import com.sharapov.database_anime.model.list.AnimeStudioCrossRef
import com.sharapov.database_anime.model.list.GenreDbModel
import com.sharapov.database_anime.model.list.RankingTypeDbModel
import com.sharapov.database_anime.model.list.StudioDbModel

@Dao
interface AnimeCoreDao {

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

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertRankingTypes(types: List<AnimeRankingTypeCrossRef>)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertAnimeGenreRefs(refs: List<AnimeGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertAnimeStudioRefs(refs: List<AnimeStudioCrossRef>)
}