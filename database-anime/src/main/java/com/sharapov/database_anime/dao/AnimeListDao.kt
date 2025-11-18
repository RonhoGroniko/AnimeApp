package com.sharapov.database_anime.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.sharapov.database_anime.model.list.AnimeListItemDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeListDao {

    @Transaction
    @Query(
        """
        SELECT DISTINCT a.* FROM anime a
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
        SELECT DISTINCT a.* FROM anime a
        JOIN anime_ranking_type art ON art.animeId = a.id
        JOIN ranking_type rt ON rt.id = art.rankingTypeId
        WHERE a.isFavorite == 1
        ORDER BY a.rating DESC
    """
    )
    fun getFavoritesAnimeList(): Flow<List<AnimeListItemDbModel>>

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
    SELECT DISTINCT a.* FROM anime a
    JOIN anime_genre_cross_ref genre ON genre.animeId = a.id
    JOIN genre g ON g.id = genre.genreId
    WHERE g.name = :genre
    ORDER BY rating DESC
    """
    )
    fun getAnimeListForGenre(genre: String): Flow<List<AnimeListItemDbModel>>
}
