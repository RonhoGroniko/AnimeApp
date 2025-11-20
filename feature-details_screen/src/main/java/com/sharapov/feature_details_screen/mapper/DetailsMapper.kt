package com.sharapov.feature_details_screen.mapper

import com.sharapov.domain_anime.entity.details.AgeRating
import com.sharapov.domain_anime.entity.details.AnimeWithDetails
import com.sharapov.domain_anime.entity.details.MediaType
import com.sharapov.domain_anime.entity.details.RelatedAnime
import com.sharapov.domain_anime.entity.details.RelationType
import com.sharapov.domain_anime.entity.details.Source
import com.sharapov.domain_anime.entity.details.Status
import com.sharapov.core_ui.theme.formatDate
import com.sharapov.feature_details_screen.model.AnimeWithDetailsUiModel
import com.sharapov.feature_details_screen.model.RelatedAnimeUiModel

fun AnimeWithDetails.toUiModel(): AnimeWithDetailsUiModel {
    return AnimeWithDetailsUiModel(
        alternativeTitles = alternativeTitles,
        averageEpisodeDuration = averageEpisodeDuration.toHoursAndMinutes(),
        background = background,
        createdAt = createdAt,
        endDate = endDate,
        genres = genres.map { it.name },
        id = id,
        mainPicture = mainPicture,
        mean = mean,
        mediaType = mediaType.toUi(),
        nsfw = nsfw,
        numEpisodes = numberOrZeroToString(numEpisodes),
        numListUsers = numListUsers,
        numScoringUsers = numScoringUsers,
        pictures = pictures,
        popularity = popularity,
        rank = rank,
        rating = rating.toUi(),
        recommendations = recommendations,
        relatedAnime = relatedAnime.toUi(),
        source = source.toUi(),
        startDate = formatDate(startDate),
        startSeason = startSeason,
        statistics = statistics,
        status = status.toUi(),
        studios = studios.map { it.name },
        synopsis = synopsis,
        title = title,
        updatedAt = updatedAt,
        isFavorite = isFavorite
    )
}

fun List<RelatedAnime>.toUi(): List<RelatedAnimeUiModel> {
    return map { it.toUi() }
}

fun RelatedAnime.toUi(): RelatedAnimeUiModel {
    return RelatedAnimeUiModel(
        anime = anime,
        relation = relation.toUi()
    )
}
fun RelationType.toUi(): String {
    return when(this) {
        RelationType.SEQUEL -> "Sequel"
        RelationType.PREQUEL -> "Prequel"
        RelationType.ALTERNATIVE_SETTING -> "Alternative"
        RelationType.ALTERNATIVE_VERSION -> "Alternative"
        RelationType.SIDE_STORY -> "Side Story"
        RelationType.PARENT_STORY -> "Parent Story"
        RelationType.SUMMARY -> "Summary"
        RelationType.FULL_STORY -> "Full Story"
        RelationType.SPINOFF -> "Spin-off"
        RelationType.ADAPTATION -> "Adaptation"
        RelationType.CHARACTER -> "Character"
        RelationType.OTHER -> "Other"
        RelationType.UNKNOWN -> "Unknown"
    }
}

fun <T> numberOrZeroToString(input: T): String {
    if (input is Int && input == 0) return "Unknown"
    if (input is Double && input == 0.0) return "Unknown"
    return input.toString()
}

fun MediaType.toUi(): String {
    return when (this) {
        MediaType.TV -> "TV"
        MediaType.OVA -> "OVA"
        MediaType.MOVIE -> "Movie"
        MediaType.SPECIAL -> "Special"
        MediaType.ONA -> "ONA"
        MediaType.MUSIC -> "Music"
        MediaType.UNKNOWN -> "Unknown"
    }
}

fun AgeRating.toUi(): String {
    return when (this) {
        AgeRating.G -> "All Ages"
        AgeRating.PG -> "Children"
        AgeRating.PG_13 -> "13+"
        AgeRating.R -> "17+"
        AgeRating.R_PLUS -> "17+"
        AgeRating.RX -> "18+"
        AgeRating.UNKNOWN -> "Unknown"
    }
}

fun Status.toUi(): String {
    return when (this) {
        Status.FINISHED -> "Finished"
        Status.AIRING -> "Airing"
        Status.NOT_YET_AIRED -> "Not out"
        Status.UNKNOWN -> "Unknown"
    }
}

fun Source.toUi(): String {
    return when (this) {
        Source.ORIGINAL -> "Original"
        Source.MANGA -> "Manga"
        Source.FOUR_KOMA_MANGA -> "4-Koma Manga"
        Source.WEB_MANGA -> "Web Manga"
        Source.DIGITAL_MANGA -> "Digital Manga"
        Source.NOVEL -> "Novel"
        Source.LIGHT_NOVEL -> "Light Novel"
        Source.VISUAL_NOVEL -> "Visual Novel"
        Source.GAME -> "Game"
        Source.CARD_GAME -> "Card Game"
        Source.BOOK -> "Book"
        Source.PICTURE_BOOK -> "Picture Book"
        Source.OTHER -> "Other"
        Source.UNKNOWN -> "Unknown"
    }
}

fun Int.toHoursAndMinutes(): String {
    if (this <= 0) return "Unknown"

    val hours = this / 3600
    val minutes = (this % 3600) / 60

    return buildString {
        if (hours > 0) append("$hours h")
        if (minutes > 0) {
            if (isNotEmpty()) append(" ")
            append("$minutes min")
        }
        if (isEmpty()) append("0 min")
    }
}