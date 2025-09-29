package com.sharapov.core_data.repository

import android.util.Log
import com.sharapov.core_data.local.database.AnimeDao
import com.sharapov.core_data.local.dbmodel.AnimeFullDbModel
import com.sharapov.core_data.mapper.toEntities
import com.sharapov.core_data.mapper.toFullDbModels
import com.sharapov.core_data.remote.retrofit.AnimeApiService
import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.repository.AnimeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeApiService: AnimeApiService,
    private val animeDao: AnimeDao
) : AnimeRepository {

    override fun getAnimeList(rankingType: RankingType): Flow<List<Anime>> {
        return animeDao.getAnimeList(rankingType.name).map { it.toEntities() }
    }

    override suspend fun updateAnimeList(rankingType: RankingType) {
        val animeList = loadAnimeList(rankingType)
        addAnimeList(animeList)
    }

    private suspend fun addAnimeList(
        animeList: List<AnimeFullDbModel>
    ) {
        animeDao.upsertFullAnime(animeList)
    }

    private suspend fun loadAnimeList(rankingType: RankingType): List<AnimeFullDbModel> {
        return try {
            animeApiService.getAnimeRankingList(rankingType.query, 40).toFullDbModels(rankingType)
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            Log.e("AnimeRepository", e.stackTraceToString())
            listOf()
        }
    }
}