package com.sharapov.core_data.remote.retrofit

import com.sharapov.core_data.remote.dto.AnimeResponseDto
import com.sharapov.core_data.remote.dto.anime_details.AnimeDetailsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiService {

    @GET("anime/ranking?fields=id,title,mean,rank,genres,created_at,studios")
    suspend fun getAnimeRankingList(
        @Query("ranking_type") rankingType: String,
        @Query("limit") limit: Int
    ): AnimeResponseDto

    @GET("anime/{id}?fields=id,title,main_picture,alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics")
    suspend fun getAnimeById(
        @Path("id") animeId: Int
    ): AnimeDetailsResponseDto
}