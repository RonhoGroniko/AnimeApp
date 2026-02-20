package com.sharapov.network_anime.mapper

import com.sharapov.core_network.mapper.toEntity
import com.sharapov.domain_anime.entity.AnimeKind
import com.sharapov.domain_anime.entity.AnimeRating
import com.sharapov.domain_anime.entity.AnimeStatus
import com.sharapov.domain_anime.entity.Genre
import com.sharapov.domain_anime.entity.GenreKind
import com.sharapov.domain_anime.entity.Studio
import com.sharapov.domain_anime.entity.details.AnimeChronology
import com.sharapov.domain_anime.entity.details.AnimeDetails
import com.sharapov.domain_anime.entity.details.Character
import com.sharapov.domain_anime.entity.details.RelatedAnime
import com.sharapov.domain_anime.entity.details.RelationKind
import com.sharapov.domain_anime.entity.details.ScoreStats
import com.sharapov.domain_anime.entity.details.Screenshot
import com.sharapov.domain_anime.entity.details.StatusKind
import com.sharapov.domain_anime.entity.details.StatusStats
import com.sharapov.domain_anime.entity.details.Video
import com.sharapov.domain_anime.entity.details.VideoKind
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.network_anime.GetAnimeByIdQuery
import com.sharapov.network_anime.type.AnimeKindEnum
import com.sharapov.network_anime.type.AnimeRatingEnum
import com.sharapov.network_anime.type.AnimeStatusEnum
import com.sharapov.network_anime.type.GenreKindEnum
import com.sharapov.network_anime.type.RelationKindEnum
import com.sharapov.network_anime.type.UserRateStatusEnum
import com.sharapov.network_anime.type.VideoKindEnum

fun GetAnimeByIdQuery.Data.toEntity(): AnimeDetails {
    val anime = this.animes.first()
    return AnimeDetails(
        id = anime.animeFields.id.toLong(),
        name = anime.animeFields.name,
        russianName = anime.russian ?: "",
        englishName = anime.english ?: "",
        japaneseName = anime.japanese ?: "",
        kind = anime.kind?.toEntity() ?: AnimeKind.UNKNOWN,
        rating = anime.rating?.toEntity() ?: AnimeRating.UNKNOWN,
        score = anime.animeFields.score ?: 0.0,
        status = anime.status?.toEntity() ?: AnimeStatus.UNKNOWN,
        episodes = anime.episodes,
        episodesAired = anime.episodesAired,
        duration = anime.duration ?: 0,
        franchise = anime.franchise ?: "",
        airedOnDate = anime.airedOn?.date.toIsoDateString(),
        releasedOnDate = anime.releasedOn?.date.toIsoDateString(),
        season = anime.season ?: "",
        imageUrl = anime.animeFields.poster?.originalUrl ?: "",
        createdAt = anime.createdAt.toIsoDateString(),
        nextEpisodeAt = anime.nextEpisodeAt.toIsoString(),
        genres = anime.genres?.map { it.toEntity() } ?: listOf(),
        studios = anime.studios.map { it.toEntity() },
        chronology = anime.chronology?.map { it.toEntity() } ?: listOf(),
        characters = anime.characterRoles?.map { it.toEntity() } ?: listOf(),
        relatedAnime = anime.related?.map { it.toEntity() } ?: listOf(),
        videos = anime.videos.map { it.toEntity() },
        screenshotsUrls = anime.screenshots.map { it.toEntity() },
        scoreStats = anime.scoresStats?.map { it.toEntity() } ?: listOf(),
        statusStats = anime.statusesStats?.map { it.toEntity() } ?: listOf(),
        description = anime.description ?: ""
    )
}

fun AnimeRatingEnum.toEntity(): AnimeRating = when (this) {
    AnimeRatingEnum.none -> AnimeRating.NONE
    AnimeRatingEnum.g -> AnimeRating.G
    AnimeRatingEnum.pg -> AnimeRating.PG
    AnimeRatingEnum.pg_13 -> AnimeRating.PG_13
    AnimeRatingEnum.r -> AnimeRating.R
    AnimeRatingEnum.r_plus -> AnimeRating.R_PLUS
    AnimeRatingEnum.rx -> AnimeRating.RX
    AnimeRatingEnum.UNKNOWN__ -> AnimeRating.UNKNOWN
}

fun AnimeStatusEnum.toEntity(): AnimeStatus = when (this) {
    AnimeStatusEnum.anons -> AnimeStatus.ANONS
    AnimeStatusEnum.ongoing -> AnimeStatus.ONGOING
    AnimeStatusEnum.released -> AnimeStatus.RELEASED
    AnimeStatusEnum.UNKNOWN__ -> AnimeStatus.UNKNOWN
}

fun GetAnimeByIdQuery.Genre.toEntity(): Genre {
    return Genre(
        id = id.toLong(),
        name = name,
        kind = kind.toEntity()
    )
}

fun GenreKindEnum.toEntity(): GenreKind = when (this) {
    GenreKindEnum.demographic -> GenreKind.DEMOGRAPHIC
    GenreKindEnum.genre -> GenreKind.GENRE
    GenreKindEnum.theme -> GenreKind.THEME
    GenreKindEnum.UNKNOWN__ -> GenreKind.UNKNOWN
}

fun GetAnimeByIdQuery.Studio.toEntity(): Studio {
    return Studio(
        id = id.toLong(),
        name = name,
        imageUrl = imageUrl ?: ""
    )
}

fun GetAnimeByIdQuery.Chronology.toEntity(): AnimeChronology {
    return AnimeChronology(
        animeListItem = animeFields.toEntity(),
        kind = kind?.toEntity() ?: AnimeKind.UNKNOWN
    )
}

fun AnimeKindEnum.toEntity(): AnimeKind = when (this) {
    AnimeKindEnum.tv -> AnimeKind.TV
    AnimeKindEnum.movie -> AnimeKind.MOVIE
    AnimeKindEnum.ova -> AnimeKind.OVA
    AnimeKindEnum.ona -> AnimeKind.ONA
    AnimeKindEnum.special -> AnimeKind.SPECIAL
    AnimeKindEnum.tv_special -> AnimeKind.TV_SPECIAL
    AnimeKindEnum.music -> AnimeKind.MUSIC
    AnimeKindEnum.pv -> AnimeKind.PV
    AnimeKindEnum.cm -> AnimeKind.CM
    AnimeKindEnum.UNKNOWN__ -> AnimeKind.UNKNOWN
}

fun GetAnimeByIdQuery.CharacterRole.toEntity(): Character {
    return Character(
        roles = rolesEn,
        id = character.id.toLong(),
        name = character.name,
        imageUrl = character.poster?.originalUrl ?: ""
    )
}

fun GetAnimeByIdQuery.Related.toEntity(): RelatedAnime {
    return RelatedAnime(
        animeListItem = anime?.animeFields?.toEntity()
            ?: AnimeListItem(
                id = 0,
                name = "",
                score = 0.0,
                imageUrl = ""
            ),
        relationKind = relationKind.toEntity()
    )
}

fun RelationKindEnum.toEntity() : RelationKind = when(this) {
    RelationKindEnum.adaptation -> RelationKind.ADAPTATION
    RelationKindEnum.alternative_setting -> RelationKind.ALTERNATIVE_SETTING
    RelationKindEnum.alternative_version -> RelationKind.ALTERNATIVE_VERSION
    RelationKindEnum.character -> RelationKind.CHARACTER
    RelationKindEnum.full_story -> RelationKind.FULL_STORY
    RelationKindEnum.other -> RelationKind.OTHER
    RelationKindEnum.parent_story -> RelationKind.PARENT_STORY
    RelationKindEnum.prequel -> RelationKind.PREQUEL
    RelationKindEnum.sequel -> RelationKind.SEQUEL
    RelationKindEnum.side_story -> RelationKind.SIDE_STORY
    RelationKindEnum.spin_off -> RelationKind.SPIN_OFF
    RelationKindEnum.summary -> RelationKind.SUMMARY
    RelationKindEnum.UNKNOWN__ -> RelationKind.UNKNOWN
}

fun GetAnimeByIdQuery.Video.toEntity(): Video {
    return Video(
        id = id.toLong(),
        name = name.formatNumberNameToEmpty(),
        kind = kind.toEntity(),
        url = url,
        imageUrl = imageUrl.formatToUrl()
    )
}

fun VideoKindEnum.toEntity(): VideoKind = when (this) {
    VideoKindEnum.pv -> VideoKind.PV
    VideoKindEnum.character_trailer -> VideoKind.CHARACTER_TRAILER
    VideoKindEnum.cm -> VideoKind.CM
    VideoKindEnum.op -> VideoKind.OP
    VideoKindEnum.ed -> VideoKind.ED
    VideoKindEnum.op_ed_clip -> VideoKind.OP_ED_CLIP
    VideoKindEnum.clip -> VideoKind.CLIP
    VideoKindEnum.other -> VideoKind.OTHER
    VideoKindEnum.episode_preview -> VideoKind.EPISODE_PREVIEW
    VideoKindEnum.UNKNOWN__ -> VideoKind.UNKNOWN
}

fun GetAnimeByIdQuery.Screenshot.toEntity(): Screenshot {
    return Screenshot(
        id = id.toLong(),
        imageUrl = originalUrl
    )
}

fun GetAnimeByIdQuery.ScoresStat.toEntity(): ScoreStats {
    return ScoreStats(
        score = score,
        count = count
    )
}

fun GetAnimeByIdQuery.StatusesStat.toEntity(): StatusStats {
    return StatusStats(
        count = count,
        kind = status.toEntity()
    )
}

fun UserRateStatusEnum.toEntity(): StatusKind = when (this) {
    UserRateStatusEnum.planned -> StatusKind.PLANNED
    UserRateStatusEnum.watching -> StatusKind.WATCHING
    UserRateStatusEnum.rewatching -> StatusKind.REWATCHING
    UserRateStatusEnum.completed -> StatusKind.COMPLETED
    UserRateStatusEnum.on_hold -> StatusKind.ON_HOLD
    UserRateStatusEnum.dropped -> StatusKind.DROPPED
    UserRateStatusEnum.UNKNOWN__ -> StatusKind.UNKNOWN
}
