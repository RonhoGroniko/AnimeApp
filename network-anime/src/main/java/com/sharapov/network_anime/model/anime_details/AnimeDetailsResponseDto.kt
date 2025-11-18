package com.sharapov.network_anime.model.anime_details


import com.sharapov.network_anime.model.anime.GenreDto
import com.sharapov.network_anime.model.anime.MainPictureDto
import com.sharapov.network_anime.model.anime.StudioDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeDetailsResponseDto(
    @SerialName("alternative_titles")
    val alternativeTitles: AlternativeTitlesDto = AlternativeTitlesDto(),
    @SerialName("average_episode_duration")
    val averageEpisodeDuration: Int = 0,
    @SerialName("background")
    val background: String = "",
    @SerialName("created_at")
    val createdAt: String = "",
    @SerialName("end_date")
    val endDate: String = "",
    @SerialName("genres")
    val genres: List<GenreDto> = listOf(),
    @SerialName("id")
    val id: Int = 0,
    @SerialName("main_picture")
    val mainPicture: MainPictureDto = MainPictureDto(),
    @SerialName("mean")
    val mean: Double = 0.0,
    @SerialName("media_type")
    val mediaType: MediaTypeDto = MediaTypeDto.UNKNOWN,
    @SerialName("nsfw")
    val nsfw: String = "",
    @SerialName("num_episodes")
    val numEpisodes: Int = 0,
    @SerialName("num_list_users")
    val numListUsers: Int = 0,
    @SerialName("num_scoring_users")
    val numScoringUsers: Int = 0,
    @SerialName("pictures")
    val pictures: List<PictureDto> = listOf(),
    @SerialName("popularity")
    val popularity: Int = 0,
    @SerialName("rank")
    val rank: Int = 0,
    @SerialName("rating")
    val rating: AgeRatingDto = AgeRatingDto.UNKNOWN,
    @SerialName("recommendations")
    val recommendations: List<RecommendationDto> = listOf(),
    @SerialName("related_anime")
    val relatedAnime: List<RelatedAnimeDto> = listOf(),
//    @SerialName("related_manga")
//    val relatedManga: List<Any?> = listOf(),
    @SerialName("source")
    val source: SourceDto = SourceDto.UNKNOWN,
    @SerialName("start_date")
    val startDate: String = "",
    @SerialName("start_season")
    val startSeason: StartSeasonDto = StartSeasonDto(),
    @SerialName("statistics")
    val statistics: StatisticsDto = StatisticsDto(),
    @SerialName("status")
    val status: StatusDto = StatusDto.UNKNOWN,
    @SerialName("studios")
    val studios: List<StudioDto> = listOf(),
    @SerialName("synopsis")
    val synopsis: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("updated_at")
    val updatedAt: String = ""
)