package com.sharapov.feature_recommendations_screen.data.repository

import com.sharapov.core_domain.Result
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.feature_recommendations_screen.data.remote.RecommendationApiService
import com.sharapov.feature_recommendations_screen.domain.RecommendationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecommendationRepositoryImpl @Inject constructor(
    private val apiService: RecommendationApiService
) : RecommendationRepository {

    override fun getRecommendations(): Flow<Result<List<AnimeListItem>>> {
        TODO("Not yet implemented")
    }
}