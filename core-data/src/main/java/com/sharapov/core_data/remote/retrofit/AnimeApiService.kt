package com.sharapov.core_data.remote.retrofit

import com.sharapov.core_data.remote.dto.AnimeResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {

    @GET("anime/ranking?fields=id,title,mean,rank,genres,created_at,studios")
    suspend fun getAnimeRankingList(
        @Query("ranking_type") rankingType: String,
        @Query("limit") limit: Int
    ): AnimeResponseDto
}