package com.sharapov.feature_recommendations_screen.domain

import com.sharapov.core_domain.Result
import com.sharapov.core_domain.entity.list.AnimeListItem
import kotlinx.coroutines.flow.Flow

interface RecommendationRepository {

    fun getRecommendations(): Flow<Result<List<AnimeListItem>>>
}