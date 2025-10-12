package com.sharapov.core_data.mapper

import com.sharapov.core_data.local.dbmodel.AnimeWithDetailsDbModel
import com.sharapov.core_data.local.dbmodel.RelatedWithAnimeDbModel
import com.sharapov.core_data.local.dbmodel.anime.AnimeDbModel
import com.sharapov.core_data.local.dbmodel.anime.AnimeListItemDbModel
import com.sharapov.core_data.local.dbmodel.anime.GenreDbModel
import com.sharapov.core_data.local.dbmodel.anime.RankingTypeDbModel
import com.sharapov.core_data.local.dbmodel.anime.StudioDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.AlternativeTitlesDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.AnimeDetailsDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.PictureDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.RecommendationsDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.RelatedAnimeDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.StartSeasonDbModel
import com.sharapov.core_data.local.dbmodel.anime_details.StatisticsDbModel
import com.sharapov.core_data.remote.dto.anime.AnimeResponseDto
import com.sharapov.core_data.remote.dto.anime.DataDto
import com.sharapov.core_data.remote.dto.anime.GenreDto
import com.sharapov.core_data.remote.dto.anime.NodeDto
import com.sharapov.core_data.remote.dto.anime.StudioDto
import com.sharapov.core_data.remote.dto.anime_details.AgeRatingDto
import com.sharapov.core_data.remote.dto.anime_details.AlternativeTitlesDto
import com.sharapov.core_data.remote.dto.anime_details.AnimeDetailsResponseDto
import com.sharapov.core_data.remote.dto.anime_details.MediaTypeDto
import com.sharapov.core_data.remote.dto.anime_details.PictureDto
import com.sharapov.core_data.remote.dto.anime_details.RecommendationDto
import com.sharapov.core_data.remote.dto.anime_details.RelatedAnimeDto
import com.sharapov.core_data.remote.dto.anime_details.RelationTypeDto
import com.sharapov.core_data.remote.dto.anime_details.SourceDto
import com.sharapov.core_data.remote.dto.anime_details.StartSeasonDto
import com.sharapov.core_data.remote.dto.anime_details.StatisticsDto
import com.sharapov.core_data.remote.dto.anime_details.StatusDto
import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.entity.Genre
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.entity.Studio
import com.sharapov.core_domain.entity.details.AgeRating
import com.sharapov.core_domain.entity.details.AlternativeTitles
import com.sharapov.core_domain.entity.details.AnimeWithDetails
import com.sharapov.core_domain.entity.details.MediaType
import com.sharapov.core_domain.entity.details.RelatedAnime
import com.sharapov.core_domain.entity.details.RelationType
import com.sharapov.core_domain.entity.details.Source
import com.sharapov.core_domain.entity.details.StartSeason
import com.sharapov.core_domain.entity.details.Statistics
import com.sharapov.core_domain.entity.details.Status
import com.sharapov.core_domain.entity.details.ViewersStatus


fun AnimeResponseDto.toListItemDbModels(rankingType: RankingType): List<AnimeListItemDbModel> {
    return data.map { it.toFullDbModel(rankingType) }
}

fun DataDto.toFullDbModel(rankingType: RankingType): AnimeListItemDbModel {
    return AnimeListItemDbModel(
        anime = node.toDbModel(),
        genres = node.genres.map { it.toDbModel() },
        studios = node.studios.map { it.toDbModel() },
        rankingTypes = listOf(rankingType.toDbModel())
    )
}

fun RankingType.toDbModel(): RankingTypeDbModel {
    return RankingTypeDbModel(
        name = name
    )
}

fun NodeDto.toDbModel(): AnimeDbModel {
    return AnimeDbModel(
        id = id,
        title = title,
        imageUrl = mainPicture.large ?: mainPicture.medium ?: "",
        rating = mean,
        createdAt = createdAt
    )
}


fun GenreDto.toDbModel(): GenreDbModel {
    return GenreDbModel(
        id = id,
        name = name
    )
}

fun StudioDto.toDbModel(): StudioDbModel {
    return StudioDbModel(
        id = id,
        name = name
    )
}

fun List<AnimeListItemDbModel>.toEntities(): List<Anime> {
    return map { it.toEntity() }
}

fun AnimeListItemDbModel.toEntity(): Anime {
    return Anime(
        id = anime.id,
        title = anime.title,
        imageUrl = anime.imageUrl,
        rating = anime.rating,
        genres = genres.map { it.toEntity() },
        createdAt = anime.createdAt,
        studios = studios.map { it.toEntity() }
    )
}

fun GenreDbModel.toEntity(): Genre {
    return Genre(
        name = name
    )
}

fun GenreDto.toEntity(): Genre {
    return Genre(
        name = name
    )
}

fun StudioDbModel.toEntity(): Studio {
    return Studio(
        name = name
    )
}

fun StudioDto.toEntity(): Studio {
    return Studio(
        name = name
    )
}


fun AnimeDetailsResponseDto.toDbModel(): AnimeDetailsDbModel {
    return AnimeDetailsDbModel(
        averageEpisodeDuration = averageEpisodeDuration,
        background = background,
        endDate = endDate,
        id = id,
        mediaType = mediaType.toEntity(),
        nsfw = nsfw,
        numEpisodes = numEpisodes,
        numListUsers = numListUsers,
        numScoringUsers = numScoringUsers,
        popularity = popularity,
        rank = rank,
        rating = rating.toEntity(),
        source = source.toEntity(),
        startDate = startDate,
        status = status.toEntity(),
        synopsis = synopsis,
        updatedAt = updatedAt,
        mean = mean
    )
}

fun SourceDto.toEntity(): Source {
    return when (this) {
        SourceDto.ORIGINAL -> Source.ORIGINAL
        SourceDto.MANGA -> Source.MANGA
        SourceDto.FOUR_KOMA_MANGA -> Source.FOUR_KOMA_MANGA
        SourceDto.WEB_MANGA -> Source.WEB_MANGA
        SourceDto.DIGITAL_MANGA -> Source.DIGITAL_MANGA
        SourceDto.NOVEL -> Source.NOVEL
        SourceDto.LIGHT_NOVEL -> Source.LIGHT_NOVEL
        SourceDto.VISUAL_NOVEL -> Source.VISUAL_NOVEL
        SourceDto.GAME -> Source.GAME
        SourceDto.CARD_GAME -> Source.CARD_GAME
        SourceDto.BOOK -> Source.BOOK
        SourceDto.PICTURE_BOOK -> Source.PICTURE_BOOK
        SourceDto.OTHER -> Source.OTHER
        SourceDto.UNKNOWN -> Source.UNKNOWN
    }
}

fun MediaTypeDto.toEntity(): MediaType {
    return when (this) {
        MediaTypeDto.TV -> MediaType.TV
        MediaTypeDto.OVA -> MediaType.OVA
        MediaTypeDto.MOVIE -> MediaType.MOVIE
        MediaTypeDto.SPECIAL -> MediaType.SPECIAL
        MediaTypeDto.ONA -> MediaType.ONA
        MediaTypeDto.MUSIC -> MediaType.MUSIC
        MediaTypeDto.UNKNOWN -> MediaType.UNKNOWN
    }
}

fun AgeRatingDto.toEntity(): AgeRating {
    return when (this) {
        AgeRatingDto.G -> AgeRating.G
        AgeRatingDto.PG -> AgeRating.PG
        AgeRatingDto.PG_13 -> AgeRating.PG_13
        AgeRatingDto.R -> AgeRating.R
        AgeRatingDto.R_PLUS -> AgeRating.R_PLUS
        AgeRatingDto.RX -> AgeRating.RX
        AgeRatingDto.UNKNOWN -> AgeRating.UNKNOWN
    }
}

fun StatusDto.toEntity(): Status {
    return when (this) {
        StatusDto.FINISHED -> Status.FINISHED
        StatusDto.AIRING -> Status.AIRING
        StatusDto.NOT_YET_AIRED -> Status.NOT_YET_AIRED
        StatusDto.UNKNOWN -> Status.UNKNOWN
    }
}

fun AlternativeTitlesDto.toDbModel(animeId: Int): AlternativeTitlesDbModel {
    return AlternativeTitlesDbModel(
        en = en,
        ja = ja,
        animeId = animeId
    )
}

fun PictureDto.toEntity(): String {
    return this.large ?: this.medium ?: ""
}

fun RecommendationDto.toDbModel(rating: Double): AnimeDbModel {
    return AnimeDbModel(
        id = node.id,
        title = node.title,
        imageUrl = node.mainPicture.large ?: node.mainPicture.medium ?: "",
        rating = rating,
        createdAt = ""
    )
}

fun RecommendationDto.toDbModel(animeId: Int): RecommendationsDbModel {
    return RecommendationsDbModel(
        animeId = animeId,
        recommendedAnimeId = node.id
    )
}

fun RelatedAnimeDto.toDbModel(animeId: Int): RelatedAnimeDbModel {
    return RelatedAnimeDbModel(
        animeId = animeId,
        relatedAnimeId = node.id,
        relation = relationType.toEntity()
    )
}

fun RelatedAnimeDto.toDbModel(rating: Double): AnimeDbModel {
    return AnimeDbModel(
        id = node.id,
        title = node.title,
        imageUrl = node.mainPicture.large ?: node.mainPicture.medium ?: "",
        rating = rating,
        createdAt = ""
    )
}

fun RelationTypeDto.toEntity(): RelationType {
    return when (this) {
        RelationTypeDto.SEQUEL -> RelationType.SEQUEL
        RelationTypeDto.PREQUEL -> RelationType.PREQUEL
        RelationTypeDto.ALTERNATIVE_SETTING -> RelationType.ALTERNATIVE_SETTING
        RelationTypeDto.ALTERNATIVE_VERSION -> RelationType.ALTERNATIVE_VERSION
        RelationTypeDto.SIDE_STORY -> RelationType.SIDE_STORY
        RelationTypeDto.PARENT_STORY -> RelationType.PARENT_STORY
        RelationTypeDto.SUMMARY -> RelationType.SUMMARY
        RelationTypeDto.FULL_STORY -> RelationType.FULL_STORY
        RelationTypeDto.SPINOFF -> RelationType.SPINOFF
        RelationTypeDto.ADAPTATION -> RelationType.ADAPTATION
        RelationTypeDto.CHARACTER -> RelationType.CHARACTER
        RelationTypeDto.OTHER -> RelationType.OTHER
        RelationTypeDto.UNKNOWN -> RelationType.UNKNOWN
    }
}

fun StartSeasonDto.toDbModel(animeId: Int): StartSeasonDbModel {
    return StartSeasonDbModel(
        season = season,
        year = year,
        animeId = animeId
    )
}

fun StatisticsDto.toDbModel(animeId: Int): StatisticsDbModel {
    return StatisticsDbModel(
        animeId = animeId,
        completed = status.completed ?: 0,
        dropped = status.dropped ?: 0,
        onHold = status.onHold ?: 0,
        planToWatch = status.planToWatch ?: 0,
        watching = status.watching ?: 0
    )
}

fun PictureDto.toDbModel(animeId: Int): PictureDbModel {
    return PictureDbModel(
        animeId = animeId,
        url = medium ?: large ?: ""
    )
}

fun AnimeWithDetailsDbModel.toEntity(): AnimeWithDetails {
    return AnimeWithDetails(
        alternativeTitles = alternativeTitles.let { it?.titles?.toEntity(it.synonyms.map { s -> s.value } ) ?: AlternativeTitles(
            en = "",
            ja = "",
            synonyms = listOf()
        ) },
        averageEpisodeDuration = details?.averageEpisodeDuration ?: 0,
        background = details?.background ?: "",
        createdAt = anime.createdAt,
        endDate = details?.endDate ?: "",
        genres = genres.map { it.toEntity() },
        id = anime.id,
        mainPicture = anime.imageUrl,
        mean = anime.rating,
        mediaType = details?.mediaType ?: MediaType.UNKNOWN,
        nsfw = details?.nsfw ?: "",
        numEpisodes = details?.numEpisodes?: 0,
        numListUsers = details?.numListUsers?: 0,
        numScoringUsers = details?.numScoringUsers?: 0,
        pictures = pictures.map { it.url },
        popularity = details?.popularity?: 0,
        rank = details?.rank?: 0,
        rating = details?.rating ?: AgeRating.UNKNOWN,
        recommendations = recommendations.map { it.toEntity() },
        relatedAnime = relatedAnime.map { it.toEntity() },
        source = details?.source ?: Source.UNKNOWN,
        startDate = details?.startDate ?: "",
        startSeason = startSeason?.toEntity() ?: StartSeason(
            season = "",
            year = 0
        ) ,
        statistics = statistics?.toEntity() ?: Statistics(
            numListUsers = 0,
            status = ViewersStatus(
                completed = 0,
                dropped = 0,
                onHold = 0,
                planToWatch = 0,
                watching = 0
            )
        ),
        status = details?.status ?: Status.UNKNOWN,
        studios = studios.map { it.toEntity() },
        synopsis = details?.synopsis?: "",
        title = anime.title,
        updatedAt = details?.updatedAt ?: ""
    )
}

fun AlternativeTitlesDbModel.toEntity(synonyms: List<String>): AlternativeTitles {
    return AlternativeTitles(
        en = en,
        ja = ja,
        synonyms = synonyms,
    )
}

fun AnimeDbModel.toEntity(): Anime {
    return Anime(
        id = id,
        title = title,
        imageUrl = imageUrl,
        rating = rating,
        genres = listOf(),
        createdAt = createdAt,
        studios = listOf()
    )
}

fun RelatedWithAnimeDbModel.toEntity(): RelatedAnime {
    return RelatedAnime(
        anime = anime.toEntity(),
        relation = link.relation
    )
}

fun StartSeasonDbModel.toEntity(): StartSeason {
    return StartSeason(
        season = season,
        year = year
    )
}

fun StatisticsDbModel.toEntity(): Statistics {
    return Statistics(
        numListUsers = completed + dropped + onHold + planToWatch + watching,
        status = ViewersStatus(
            completed = completed,
            dropped = dropped,
            onHold = onHold,
            planToWatch = planToWatch,
            watching = watching
        )
    )
}