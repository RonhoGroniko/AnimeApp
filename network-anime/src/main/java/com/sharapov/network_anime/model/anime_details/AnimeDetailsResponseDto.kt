package com.sharapov.network_anime.model.anime_details


import com.sharapov.network_anime.model.anime.StudioDto
import com.sharapov.network_anime.model.common.GenreDto
import com.sharapov.network_anime.model.common.MainPictureDto
import com.sharapov.network_anime.serializers.FlexibleIntNullableSerializer
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

@Serializable
data class AlternativeTitlesDto(
    @SerialName("en")
    val en: String = "",
    @SerialName("ja")
    val ja: String = "",
    @SerialName("synonyms")
    val synonyms: List<String> = listOf()
)

@Serializable
enum class MediaTypeDto {
    @SerialName("tv")
    TV,

    @SerialName("ova")
    OVA,

    @SerialName("movie")
    MOVIE,

    @SerialName("special")
    SPECIAL,

    @SerialName("ona")
    ONA,

    @SerialName("music")
    MUSIC,

    @SerialName("unknown")
    UNKNOWN
}

@Serializable
data class PictureDto(
    @SerialName("large")
    val large: String? = "",
    @SerialName("medium")
    val medium: String? = ""
)

@Serializable
enum class AgeRatingDto {
    @SerialName("g")
    G,

    @SerialName("pg")
    PG,

    @SerialName("pg_13")
    PG_13,

    @SerialName("r")
    R,

    @SerialName("r+")
    R_PLUS,

    @SerialName("rx")
    RX,

    @SerialName("unknown")
    UNKNOWN
}

@Serializable
data class RecommendationDto(
    @SerialName("node")
    val node: NodeDetailsDto = NodeDetailsDto(),
    @SerialName("num_recommendations")
    val numRecommendations: Int = 0
)

@Serializable
data class RelatedAnimeDto(
    @SerialName("node")
    val node: NodeDetailsDto = NodeDetailsDto(),
    @SerialName("relation_type")
    val relationType: RelationTypeDto = RelationTypeDto.UNKNOWN,
    @SerialName("relation_type_formatted")
    val relationTypeFormatted: String = ""
)

@Serializable
data class NodeDetailsDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("main_picture")
    val mainPicture: MainPictureDto = MainPictureDto(),
    @SerialName("title")
    val title: String = ""
)

@Serializable
enum class RelationTypeDto {
    @SerialName("sequel")
    SEQUEL,

    @SerialName("prequel")
    PREQUEL,

    @SerialName("alternative_setting")
    ALTERNATIVE_SETTING,

    @SerialName("alternative_version")
    ALTERNATIVE_VERSION,

    @SerialName("side_story")
    SIDE_STORY,

    @SerialName("parent_story")
    PARENT_STORY,

    @SerialName("summary")
    SUMMARY,

    @SerialName("full_story")
    FULL_STORY,

    @SerialName("spinoff")
    SPINOFF,

    @SerialName("adaptation")
    ADAPTATION,

    @SerialName("character")
    CHARACTER,

    @SerialName("other")
    OTHER,

    @SerialName("unknown")
    UNKNOWN
}

@Serializable
enum class SourceDto {
    @SerialName("original")
    ORIGINAL,
    @SerialName("manga")
    MANGA,
    @SerialName("4_koma_manga")
    FOUR_KOMA_MANGA, // Манга в формате 4-кома (четыре кадра, юмор)
    @SerialName("web_manga")
    WEB_MANGA,
    @SerialName("digital_manga")
    DIGITAL_MANGA,
    @SerialName("novel")
    NOVEL,
    @SerialName("light_novel")
    LIGHT_NOVEL,
    @SerialName("visual_novel")
    VISUAL_NOVEL,
    @SerialName("game")
    GAME,
    @SerialName("card_game")
    CARD_GAME,
    @SerialName("book")
    BOOK,
    @SerialName("picture_book")
    PICTURE_BOOK,
    @SerialName("other")
    OTHER,
    @SerialName("unknown")
    UNKNOWN
}

@Serializable
data class StartSeasonDto(
    @SerialName("season")
    val season: String = "",
    @SerialName("year")
    val year: Int = 0
)

@Serializable
data class StatisticsDto(
    @SerialName("num_list_users")
    val numListUsers: Int = 0,
    @SerialName("status")
    val status: StatusDetailsDto = StatusDetailsDto()
)

@Serializable
data class StatusDetailsDto(
    @SerialName("completed")
    @Serializable(with = FlexibleIntNullableSerializer::class)
    val completed: Int? = 0,
    @SerialName("dropped")
    @Serializable(with = FlexibleIntNullableSerializer::class)
    val dropped: Int? = 0,
    @SerialName("on_hold")
    @Serializable(with = FlexibleIntNullableSerializer::class)
    val onHold: Int? = 0,
    @SerialName("plan_to_watch")
    @Serializable(with = FlexibleIntNullableSerializer::class)
    val planToWatch: Int? = 0,
    @SerialName("watching")
    @Serializable(with = FlexibleIntNullableSerializer::class)
    val watching: Int? = 0
)

@Serializable
enum class StatusDto {
    @SerialName("finished_airing")
    FINISHED,

    @SerialName("currently_airing")
    AIRING,

    @SerialName("not_yet_aired")
    NOT_YET_AIRED,

    @SerialName("unknown")
    UNKNOWN;
}