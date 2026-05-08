package com.sharapov.feature_recommendations_screen.data.remote

import com.sharapov.feature_recommendations_screen.data.remote.dto.RecommendRequestDto
import com.sharapov.feature_recommendations_screen.data.remote.dto.RecommendResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface RecommendationApiService {

    @POST("recommend")
    suspend fun getRecommendations(
        @Body requestDto: RecommendRequestDto
    ) : RecommendResponseDto
}