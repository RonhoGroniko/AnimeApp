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
import com.sharapov.core_data.local.dbmodel.AnimeStudioCrossRef
import com.sharapov.core_data.local.dbmodel.GenreDbModel
import com.sharapov.core_data.local.dbmodel.StudioDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Transaction
    @Query("SELECT * FROM anime WHERE rankingType=:rankingType")
    fun getAnimeList(rankingType: String): Flow<List<AnimeFullDbModel>>

    @Upsert
    suspend fun upsertAnime(animeList: List<AnimeDbModel>)

    @Upsert
    suspend fun upsertGenres(genres: List<GenreDbModel>)

    @Upsert
    suspend fun upsertStudios(studios: List<StudioDbModel>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnimeGenreRefs(refs: List<AnimeGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnimeStudioRefs(refs: List<AnimeStudioCrossRef>)

    @Transaction
    suspend fun upsertFullAnime(animeFullDbModelList: List<AnimeFullDbModel>) {

    }
}