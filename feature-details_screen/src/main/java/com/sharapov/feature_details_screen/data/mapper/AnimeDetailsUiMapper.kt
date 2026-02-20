package com.sharapov.feature_details_screen.data.mapper

import com.sharapov.core_domain.entity.AnimeStatus
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.feature_details_screen.domain.entity.AnimeDetails
import com.sharapov.feature_details_screen.ui.model.AnimeDetailsUiModel

fun AnimeDetails.toUi(isFavorite: Boolean) : AnimeDetailsUiModel {
    return AnimeDetailsUiModel(
        id = id,
        name = name,
        isFavorite = isFavorite,
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

fun AnimeDetailsUiModel.toEntityListItem(): AnimeListItem {
    return AnimeListItem(
        id = id,
        name = name,
        score = score,
        imageUrl = imageUrl
    )
}