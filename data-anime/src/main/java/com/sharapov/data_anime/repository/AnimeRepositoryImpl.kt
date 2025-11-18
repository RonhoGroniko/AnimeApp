package com.sharapov.data_anime.repository

import com.sharapov.network_anime.DataException
import com.sharapov.network_anime.retrofit.AnimeApiService
import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.entity.RankingType
import com.sharapov.domain_anime.entity.details.AnimeWithDetails
import com.sharapov.domain_anime.repository.AnimeRepository
import com.sharapov.domain_anime.usecases.anime.list.AnimeFilter
import com.sharapov.data_anime.mapper.toDbModel
import com.sharapov.data_anime.mapper.toEntities
import com.sharapov.data_anime.mapper.toEntity
import com.sharapov.data_anime.mapper.toListItemDbModels
import com.sharapov.database_anime.model.database.AnimeDao
import com.sharapov.database_anime.model.details.AlternativeTitleSynonymDbModel
import com.sharapov.database_anime.model.list.AnimeListItemDbModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.collections.map

class AnimeRepositoryImpl @Inject constructor(
    private val animeApiService: AnimeApiService,
    private val animeDao: AnimeDao
) : AnimeRepository {

    override fun getAnimeList(filter: AnimeFilter): Flow<List<Anime>> {
        return when (filter) {
            AnimeFilter.All -> animeDao.getAnimeList().map { it.toEntities() }
            is AnimeFilter.ByGenre -> animeDao.getAnimeListForGenre(filter.genre)
                .map { it.toEntities() }

            is AnimeFilter.ByRankingType -> animeDao.getAnimeListForRankingType(filter.rankingType.name)
                .map { it.toEntities() }

            AnimeFilter.Favorites -> animeDao.getFavoritesAnimeList().map { it.toEntities() }
        }
    }

    override suspend fun updateAnimeList(rankingType: RankingType, limit: Int) {
        val animeList = loadAnimeList(rankingType, limit)
        addAnimeList(animeList)
    }

    override fun getAnimeById(animeId: Int): Flow<AnimeWithDetails> =
        animeDao.getAnimeWithDetails(animeId)
            .onStart {
                if (!animeDao.hasDetails(animeId)) {
                    loadAnimeById(animeId)
                }
            }
            .filterNotNull()
            .map { it.toEntity() }
            .distinctUntilChanged()

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

        animeDao.upsertDetailsBundle(
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
            AnimeFilter.All -> animeDao.searchAnime(query).map { it.toEntities() }
            is AnimeFilter.ByGenre -> animeDao.searchAnimeByGenre(query, filter.genre).map { it.toEntities() }
            is AnimeFilter.ByRankingType -> animeDao.searchAnimeByRankingType(query, filter.rankingType.name).map { it.toEntities() }
            AnimeFilter.Favorites -> animeDao.searchFavoritesAnime(query).map { it.toEntities() }
        }
    }

    override suspend fun changeAnimeFavoriteStatus(animeId: Int) {
        animeDao.changeAnimeFavoriteStatus(animeId)
    }

    private suspend fun addAnimeList(
        animeList: List<AnimeListItemDbModel>
    ) {
        animeDao.upsertFullAnime(animeList)
    }

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