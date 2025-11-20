package com.sharapov.data_anime.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.sharapov.data_anime.mapper.toEntities
import com.sharapov.data_anime.mapper.toListItemDbModels
import com.sharapov.database_anime.AnimeLocalDataSource
import com.sharapov.database_anime.dao.AnimeListDao
import com.sharapov.database_anime.model.list.AnimeDbModel
import com.sharapov.database_anime.model.list.AnimeListItemDbModel
import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.entity.RankingType
import com.sharapov.domain_anime.entity.common.AnimeFilter
import com.sharapov.domain_anime.repository.ListAnimeRepository
import com.sharapov.network_anime.DataException
import com.sharapov.network_anime.model.anime.AnimeResponseDto
import com.sharapov.network_anime.retrofit.AnimeApiService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class ListAnimeRepositoryImpl @Inject constructor(
    private val animeApiService: AnimeApiService,
    private val animeListDao: AnimeListDao,
    private val animeLocalDataSource: AnimeLocalDataSource
) : ListAnimeRepository {

    override fun getAnimeList(filter: AnimeFilter): Flow<List<Anime>> {
        return when (filter) {
            AnimeFilter.All -> animeListDao.getAnimeList().map { it.toEntities() }
            is AnimeFilter.ByGenre -> animeListDao.getAnimeListForGenre(filter.genre)
                .map { it.toEntities() }

            is AnimeFilter.ByRankingType -> animeListDao.getAnimeListForRankingType(filter.rankingType.name)
                .map { it.toEntities() }

            AnimeFilter.Favorites -> animeListDao.getFavoritesAnimeList().map { it.toEntities() }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    // TODO: ЗАТЫЧКА ЭНИВЕЙ УБИРАТЬ
    override suspend fun updateAnimeList(rankingType: RankingType, limit: Int) {
        val favoritesIds = animeListDao.getFavoriteIds()
        val animeList = loadAnimeList(rankingType, limit).toListItemDbModels(rankingType).map {
            AnimeListItemDbModel(
                anime = AnimeDbModel(
                    id = it.anime.id,
                    title = it.anime.title,
                    imageUrl = it.anime.imageUrl,
                    rating = it.anime.rating,
                    createdAt = it.anime.createdAt,
                    isFavorite = favoritesIds.contains(it.anime.id.toLong())
                ),
                genres = it.genres,
                studios = it.studios,
                rankingTypes = it.rankingTypes
            )
        }
        addAnimeList(animeList)
    }

    private suspend fun addAnimeList(
        animeList: List<AnimeListItemDbModel>
    ) {
        animeLocalDataSource.upsertFullAnime(animeList)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    // TODO: ЗАТЫЧКА ЭНИВЕЙ УБИРАТЬ
    private suspend fun loadAnimeList(
        rankingType: RankingType,
        limit: Int
    ): AnimeResponseDto {
        return try {
            animeApiService.getAnimeRankingList(rankingType.query, limit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            throw DataException.Network(e)
        } catch (e: HttpException) {
            throw DataException.Server(e)
        } catch (e: Exception) {
            throw DataException.Unknown(e)
        }
    }
}