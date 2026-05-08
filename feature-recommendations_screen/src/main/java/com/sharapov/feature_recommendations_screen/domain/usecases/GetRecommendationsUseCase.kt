package com.sharapov.feature_recommendations_screen.domain.usecases

import com.sharapov.core_domain.Result
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.feature_recommendations_screen.domain.repository.RecommendationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val repository: RecommendationRepository
) {

    operator fun invoke(): Flow<Result<List<AnimeListItem>>> {
        return repository.getRecommendations()
    }
}