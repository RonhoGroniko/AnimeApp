package com.sharapov.data_anime.repository

import com.sharapov.data_anime.mapper.toEntities
import com.sharapov.database_anime.dao.AnimeSearchDao
import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.entity.common.AnimeFilter
import com.sharapov.domain_anime.repository.SearchAnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchAnimeRepositoryImpl @Inject constructor(
    private val animeSearchDao: AnimeSearchDao
) : SearchAnimeRepository {

    override fun searchAnimeByTitle(query: String, filter: AnimeFilter): Flow<List<Anime>> {
        return when (filter) {
            AnimeFilter.All -> animeSearchDao.searchAnime(query).map { it.toEntities() }
            is AnimeFilter.ByGenre -> animeSearchDao.searchAnimeByGenre(query, filter.genre)
                .map { it.toEntities() }

            is AnimeFilter.ByRankingType -> animeSearchDao.searchAnimeByRankingType(
                query,
                filter.rankingType.name
            ).map { it.toEntities() }

            AnimeFilter.Favorites -> animeSearchDao.searchFavoritesAnime(query)
                .map { it.toEntities() }
        }
    }
}