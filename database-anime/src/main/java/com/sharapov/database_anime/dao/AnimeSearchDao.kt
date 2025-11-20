package com.sharapov.database_anime.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.sharapov.database_anime.model.list.AnimeListItemDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeSearchDao {

    @Transaction
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
    fun searchAnime(query: String): Flow<List<AnimeListItemDbModel>>

    @Transaction
    @Query(
        """
    SELECT DISTINCT a.* FROM anime a
    LEFT JOIN alternative_titles altT ON a.id = altT.animeId
    LEFT JOIN alternative_title_synonyms syn ON altT.animeId = syn.animeId
    JOIN anime_ranking_type art ON art.animeId = a.id
    JOIN ranking_type rt ON rt.id = art.rankingTypeId
    WHERE rt.name = :rankingType AND (a.title LIKE '%' || :query || '%' OR altT.en LIKE '%' || :query || '%'
       OR altT.ja LIKE '%' || :query || '%'
       OR syn.value LIKE '%' || :query || '%')
    """
    )
    fun searchAnimeByRankingType(query: String, rankingType: String): Flow<List<AnimeListItemDbModel>>

    @Transaction
    @Query(
        """
    SELECT DISTINCT a.* FROM anime a
    LEFT JOIN alternative_titles altT ON a.id = altT.animeId
    LEFT JOIN alternative_title_synonyms syn ON altT.animeId = syn.animeId
    JOIN anime_genre_cross_ref genre ON genre.animeId = a.id
    JOIN genre g ON g.id = genre.genreId
    WHERE  g.name = :genre AND (a.title LIKE '%' || :query || '%' OR altT.en LIKE '%' || :query || '%'
       OR altT.ja LIKE '%' || :query || '%'
       OR syn.value LIKE '%' || :query || '%')
    """
    )
    fun searchAnimeByGenre(query: String, genre: String): Flow<List<AnimeListItemDbModel>>

    @Transaction
    @Query(
        """
    SELECT DISTINCT a.* FROM anime a
    LEFT JOIN alternative_titles altT ON a.id = altT.animeId
    LEFT JOIN alternative_title_synonyms syn ON altT.animeId = syn.animeId
    WHERE a.isFavorite == 1 AND (a.title LIKE '%' || :query || '%' OR altT.en LIKE '%' || :query || '%'
       OR altT.ja LIKE '%' || :query || '%'
       OR syn.value LIKE '%' || :query || '%')
    """
    )
    fun searchFavoritesAnime(query: String): Flow<List<AnimeListItemDbModel>>

}
