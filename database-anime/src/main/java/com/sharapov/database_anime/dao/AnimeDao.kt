package com.sharapov.database_anime.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.sharapov.database_anime.model.AnimeDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM favorites ORDER BY createdAt DESC")
    suspend fun getFavoriteAnimeList() : List<AnimeDbModel>

    @Insert(onConflict = REPLACE)
    suspend fun addFavoriteAnime(anime: AnimeDbModel)

    @Query("DELETE FROM favorites WHERE id = :animeId")
    suspend fun removeFromFavorites(animeId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :animeId)")
    fun getFavoriteStatus(animeId: Long) : Flow<Boolean>
}