package com.sharapov.core_data.repository

import com.sharapov.core_data.local.database.AnimeDao
import com.sharapov.core_data.local.dbmodel.AnimeFullDbModel
import com.sharapov.core_data.mapper.toEntities
import com.sharapov.core_data.mapper.toEntity
import com.sharapov.core_data.mapper.toFullDbModels
import com.sharapov.core_data.remote.DataException
import com.sharapov.core_data.remote.retrofit.AnimeApiService
import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.entity.details.AnimeWithDetails
import com.sharapov.core_domain.repository.AnimeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeApiService: AnimeApiService,
    private val animeDao: AnimeDao
) : AnimeRepository {

    override fun getAnimeList(rankingType: RankingType): Flow<List<Anime>> {
        return animeDao.getAnimeList(rankingType.name).map { it.toEntities() }
    }

    override suspend fun updateAnimeList(rankingType: RankingType, limit: Int) {
        val animeList = loadAnimeList(rankingType, limit)
        addAnimeList(animeList)
    }

    override suspend fun getAnimeById(animeId: Int): AnimeWithDetails {
        return try {
            animeApiService.getAnimeById(animeId).toEntity()
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

    private suspend fun addAnimeList(
        animeList: List<AnimeFullDbModel>
    ) {
        animeDao.upsertFullAnime(animeList)
    }

    private suspend fun loadAnimeList(rankingType: RankingType, limit: Int): List<AnimeFullDbModel> {
        return try {
            animeApiService.getAnimeRankingList(rankingType.query, limit).toFullDbModels(rankingType)
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