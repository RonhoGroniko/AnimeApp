package com.sharapov.feature_details_screen.mapper

import com.sharapov.domain_anime.entity.AnimeStatus
import com.sharapov.domain_anime.entity.details.AnimeDetails
import com.sharapov.feature_details_screen.model.AnimeDetailsUiModel

fun AnimeDetails.toUi() : AnimeDetailsUiModel {
    return AnimeDetailsUiModel(
        id = id,
        name = name,
        russianName = russianName,
        englishName = englishName,
        japaneseName = japaneseName,
        kind = kind.value,
        rating = rating.value,
        score = score,
        status = status.value,
        episodes = episodes,
        episodesAired = episodesAired,
        duration = duration,
        franchise = franchise,
        airedOnDate = airedOnDate,
        releasedOnDate = if (status == AnimeStatus.RELEASED) releasedOnDate else "",
        season = season,
        imageUrl = imageUrl,
        createdAt = createdAt,
        nextEpisodeAt = nextEpisodeAt,
        genres = genres.map { it.name },
        studios = studios.map { it.name },
        chronology = chronology,
        characters = characters,
        relatedAnime = relatedAnime.filter { it.animeListItem.imageUrl.isNotBlank() },
        videos = videos.filter { it.imageUrl.isNotBlank() },
        screenshotsUrls = screenshotsUrls,
        scoreStats = scoreStats,
        statusStats = statusStats,
        description = description
    )
}