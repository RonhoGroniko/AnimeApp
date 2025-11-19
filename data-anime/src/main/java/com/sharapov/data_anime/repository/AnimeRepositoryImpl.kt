package com.sharapov.data_anime.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.sharapov.data_anime.mapper.toDbModel
import com.sharapov.data_anime.mapper.toEntities
import com.sharapov.data_anime.mapper.toEntity
import com.sharapov.data_anime.mapper.toListItemDbModels
import com.sharapov.database_anime.AnimeLocalDataSource
import com.sharapov.database_anime.dao.AnimeDetailsDao
import com.sharapov.database_anime.dao.AnimeListDao
import com.sharapov.database_anime.dao.AnimeSearchDao
import com.sharapov.database_anime.model.details.AlternativeTitleSynonymDbModel
import com.sharapov.database_anime.model.list.AnimeListItemDbModel
import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.entity.RankingType
import com.sharapov.domain_anime.entity.details.AnimeWithDetails
import com.sharapov.domain_anime.repository.AnimeRepository
import com.sharapov.domain_anime.usecases.anime.list.AnimeFilter
import com.sharapov.network_anime.DataException
import com.sharapov.network_anime.retrofit.AnimeApiService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.io.IOException
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeApiService: AnimeApiService,
    private val animeListDao: AnimeListDao,
    private val animeDetailsDao: AnimeDetailsDao,
    private val animeSearchDao: AnimeSearchDao,
    private val animeLocalDataSource: AnimeLocalDataSource
) : AnimeRepository {

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
        val animeList = loadAnimeList(rankingType, limit)
        addAnimeList(animeList)
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    // TODO: ЗАТЫЧКА ЭНИВЕЙ УБИРАТЬ
    override fun getAnimeById(animeId: Int): Flow<AnimeWithDetails> =
        animeDetailsDao.getAnimeWithDetails(animeId)
            .onStart {
                if (!animeDetailsDao.hasDetails(animeId)) {
                    loadAnimeById(animeId)
                }
            }
            .filterNotNull()
            .map { it.toEntity() }
            .distinctUntilChanged()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    // TODO: ЗАТЫЧКА ЭНИВЕЙ УБИРАТЬ
    override suspend fun loadAnimeById(animeId: Int) {
        val dto = try {
            animeApiService.getAnimeById(animeId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            throw DataException.Network(e)
        } catch (e: HttpException) {
            throw DataException.Server(e)
        } catch (e: Exception) {
            throw DataException.Unknown(e)
        }

        val details = dto.toDbModel()
        val statistics = dto.statistics.toDbModel(animeId)
        val startSeason = dto.startSeason.toDbModel(animeId)
        val alternativeTitles = dto.alternativeTitles.toDbModel(animeId)
        val synonyms = dto.alternativeTitles.synonyms
            .map { value -> AlternativeTitleSynonymDbModel(animeId = animeId, value = value) }
        val recommendedAnime = dto.recommendations.map { it.toDbModel(details.mean) }
        val recommendationsLinks = dto.recommendations.map { it.toDbModel(animeId) }
        val relatedAnime = dto.relatedAnime.map { it.toDbModel(details.mean) }
        val relatedLinks = dto.relatedAnime.map { it.toDbModel(animeId) }
        val pictures = dto.pictures.map { it.toDbModel(animeId) }
        val genres = dto.genres.map { it.toDbModel() }

        animeLocalDataSource.upsertDetailsBundle(
            details = details,
            statistics = statistics,
            startSeason = startSeason,
            alternativeTitles = alternativeTitles,
            synonyms = synonyms,
            recommendedAnime = recommendedAnime,
            recommendationsLinks = recommendationsLinks,
            relatedLinks = relatedLinks,
            pictures = pictures,
            relatedAnime = relatedAnime,
            genres = genres
        )
    }

    override fun searchAnimeByTitle(query: String, filter: AnimeFilter): Flow<List<Anime>> {
        return when(filter) {
            AnimeFilter.All -> animeSearchDao.searchAnime(query).map { it.toEntities() }
            is AnimeFilter.ByGenre -> animeSearchDao.searchAnimeByGenre(query, filter.genre).map { it.toEntities() }
            is AnimeFilter.ByRankingType -> animeSearchDao.searchAnimeByRankingType(query, filter.rankingType.name).map { it.toEntities() }
            AnimeFilter.Favorites -> animeSearchDao.searchFavoritesAnime(query).map { it.toEntities() }
        }
    }

    override suspend fun changeAnimeFavoriteStatus(animeId: Int) {
        animeDetailsDao.changeAnimeFavoriteStatus(animeId)
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
    ): List<AnimeListItemDbModel> {
        return try {
            animeApiService.getAnimeRankingList(rankingType.query, limit)
                .toListItemDbModels(rankingType)
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