package com.sharapov.core_data.mapper

import com.sharapov.core_data.local.dbmodel.AnimeDbModel
import com.sharapov.core_data.local.dbmodel.AnimeFullDbModel
import com.sharapov.core_data.local.dbmodel.GenreDbModel
import com.sharapov.core_data.local.dbmodel.StudioDbModel
import com.sharapov.core_data.remote.dto.AnimeResponseDto
import com.sharapov.core_data.remote.dto.DataDto
import com.sharapov.core_data.remote.dto.GenreDto
import com.sharapov.core_data.remote.dto.NodeDto
import com.sharapov.core_data.remote.dto.StudioDto
import com.sharapov.core_data.remote.dto.anime_details.AgeRatingDto
import com.sharapov.core_data.remote.dto.anime_details.AlternativeTitlesSoloDto
import com.sharapov.core_data.remote.dto.anime_details.AnimeSoloResponseDto
import com.sharapov.core_data.remote.dto.anime_details.MediaTypeDto
import com.sharapov.core_data.remote.dto.anime_details.PictureSoloDto
import com.sharapov.core_data.remote.dto.anime_details.RecommendationSoloDto
import com.sharapov.core_data.remote.dto.anime_details.RelatedAnimeSoloDto
import com.sharapov.core_data.remote.dto.anime_details.RelationTypeDto
import com.sharapov.core_data.remote.dto.anime_details.SourceDto
import com.sharapov.core_data.remote.dto.anime_details.StartSeasonSoloDto
import com.sharapov.core_data.remote.dto.anime_details.StatisticsSoloDto
import com.sharapov.core_data.remote.dto.anime_details.StatusDto
import com.sharapov.core_data.remote.dto.anime_details.StatusSoloDto
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


fun AnimeResponseDto.toFullDbModels(rankingType: RankingType): List<AnimeFullDbModel> {
    return data.map { it.toFullDbModel(rankingType) }
}

fun DataDto.toFullDbModel(rankingType: RankingType): AnimeFullDbModel {
    return AnimeFullDbModel(
        anime = node.toDbModel(rankingType),
        genres = node.genres.map { it.toDbModel() },
        studios = node.studios.map { it.toDbModel() }
    )
}

fun NodeDto.toDbModel(rankingType: RankingType): AnimeDbModel {
    return AnimeDbModel(
        id = id,
        title = title,
        imageUrl = mainPicture.medium,
        rating = mean,
        rank = rank,
        createdAt = createdAt,
        rankingType = rankingType.name
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

fun List<AnimeFullDbModel>.toEntities(): List<Anime> {
    return map { it.toEntity() }
}

fun AnimeFullDbModel.toEntity(): Anime {
    return Anime(
        id = anime.id,
        title = anime.title,
        imageUrl = anime.imageUrl,
        rating = anime.rating,
        rank = anime.rank,
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

// TODO: PICTURE null

fun AnimeSoloResponseDto.toEntity(): AnimeWithDetails {
    return AnimeWithDetails(
        alternativeTitles = alternativeTitles.toEntity(),
        averageEpisodeDuration = averageEpisodeDuration,
        background = background,
        createdAt = createdAt,
        endDate = endDate,
        genres = genres.map { it.toEntity() },
        id = id,
        mainPicture = mainPicture.large,
        mean = mean,
        mediaType = mediaType.toEntity(),
        nsfw = nsfw,
        numEpisodes = numEpisodes,
        numListUsers = numListUsers,
        numScoringUsers = numScoringUsers,
        pictures = pictures.map { it.toEntity() },
        popularity = popularity,
        rank = rank,
        rating = rating.toEntity(),
        recommendations = recommendations.map { it.toEntity() },
        relatedAnime = relatedAnime.map { it.toEntity() },
        source = source.toEntity(),
        startDate = startDate,
        startSeason = startSeason.toEntity(),
        statistics = statistics.toEntity(),
        status = status.toEntity(),
        studios = studios.map { it.toEntity() },
        synopsis = synopsis,
        title = title,
        updatedAt = updatedAt
    )
}

fun SourceDto.toEntity(): Source {
    return when(this) {
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
    return when(this) {
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
    return  when(this) {
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
    return when(this) {
        StatusDto.FINISHED -> Status.FINISHED
        StatusDto.AIRING -> Status.AIRING
        StatusDto.NOT_YET_AIRED -> Status.NOT_YET_AIRED
        StatusDto.UNKNOWN -> Status.UNKNOWN
    }
}

fun AlternativeTitlesSoloDto.toEntity(): AlternativeTitles {
    return AlternativeTitles(
        en = en,
        ja = ja,
        synonyms = synonyms
    )
}

fun PictureSoloDto.toEntity(): String {
    return this.large
}


// TODO REFACTOR THIS SHIT
fun RecommendationSoloDto.toEntity(): Anime {
    return Anime(
        id = node.id,
        title = node.title,
        imageUrl = node.mainPicture.large,
        rating = 0.0,
        rank = 0,
        genres = listOf(),
        createdAt = "",
        studios = listOf()
    )
}

fun RelatedAnimeSoloDto.toEntity(): RelatedAnime {
    return RelatedAnime(
        anime = Anime(
            id = node.id,
            title = node.title,
            imageUrl = node.mainPicture.large,
            rating = 0.0,
            rank = 0,
            genres = listOf(),
            createdAt = "",
            studios = listOf()
        ),
        relation = relationType.toEntity()
    )
}

fun RelationTypeDto.toEntity(): RelationType {
    return when(this) {
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
fun StartSeasonSoloDto.toEntity(): StartSeason {
    return StartSeason(
        season = season,
        year = year
    )
}

fun StatisticsSoloDto.toEntity(): Statistics {
    return Statistics(
        numListUsers = numListUsers,
        status = status.toEntity()
    )
}

fun StatusSoloDto.toEntity(): ViewersStatus {
    return ViewersStatus(
        completed = completed ?: 0,
        dropped = dropped ?: 0,
        onHold = onHold ?: 0,
        planToWatch = planToWatch ?: 0,
        watching = watching ?: 0
    )
}
