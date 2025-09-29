package com.sharapov.core_data.repository

import android.util.Log
import com.sharapov.core_data.local.dbmodel.AnimeDbModel
import com.sharapov.core_data.mapper.toDbModels
import com.sharapov.core_data.remote.dto.RankingType
import com.sharapov.core_data.remote.retrofit.AnimeApiService
import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.repository.AnimeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.collections.listOf

class AnimeRepositoryImpl @Inject constructor(
    private val animeApiService: AnimeApiService
) : AnimeRepository {

    override fun getAnimeList(): Flow<List<Anime>> {
        TODO()
    }

    private suspend fun loadAnimeList(): List<AnimeDbModel> {
        return try {
            val test = animeApiService.getAnimeRankingList(RankingType.ALL.query, 40).toDbModels()
            Log.d("Load", test.toString())
            test
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            Log.e("AnimeRepository", e.stackTraceToString())
            listOf()
        }
    }
}