package com.sharapov.core_data.remote.retrofit

import com.sharapov.core_data.remote.dto.AnimeResponseDto
import retrofit2.http.GET

interface AnimeApiService {

    @GET("anime/ranking?ranking_type=all&limit=100&fields=id,title,mean,rank,genres,created_at,studios")
    suspend fun getAnimeList(): AnimeResponseDto
}