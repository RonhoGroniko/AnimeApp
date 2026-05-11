package com.sharapov.feature_recommendations_screen.data.repository

import android.util.Log
import com.sharapov.core_domain.Result
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.database_anime.dao.AnimeDao
import com.sharapov.feature_recommendations_screen.data.mapper.toEntity
import com.sharapov.feature_recommendations_screen.data.remote.RecommendationApiService
import com.sharapov.feature_recommendations_screen.data.remote.dto.RecommendRequestDto
import com.sharapov.feature_recommendations_screen.domain.repository.RecommendationRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecommendationRepositoryImpl @Inject constructor(
    private val apiService: RecommendationApiService,
    private val animeDao: AnimeDao
) : RecommendationRepository {

    override fun getRecommendations(): Flow<Result<List<AnimeListItem>>> = flow {
        emit(Result.Loading)
        try {
            val ids = animeDao.getFavoriteIds()
            val recommendations = apiService.getRecommendations(
                requestDto = RecommendRequestDto(
                    animeIds = ids,
                )
            )
            emit(Result.Success(recommendations.toEntity()))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.d("Recommendation", e.toString())
            emit(Result.Error(
                exception = e,
                message = e.message
            ))
        }
    }.flowOn(Dispatchers.IO)
}