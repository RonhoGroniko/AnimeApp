package com.sharapov.feature_search_screen.ui.model

import com.sharapov.core_domain.entity.AnimeStatus
import com.sharapov.feature_search_screen.domain.entity.AnimeFilter
import com.sharapov.feature_search_screen.domain.entity.genre.Genre

fun AnimeFilter.toUi(): AnimeFilterUiModel =
    AnimeFilterUiModel(
        order = order,
        kind = kind,
        status = status?.toUi(),
        season = season,
        rating = rating,
        origin = origin,
        genre = genre?.split(",")?.filter { it.isNotBlank() }
            ?.map { genreIdString -> Genre.getById(genreIdString.toInt()).value },
        studio = studio,
        franchise = franchise,
        censored = censored
    )

fun AnimeFilterUiModel.toEntity(): AnimeFilter =
    AnimeFilter(
        order = order,
        kind = kind,
        status = status?.toEntity(),
        season = season,
        rating = rating,
        origin = origin,
        genre = genre?.filter { it.isNotBlank() }?.joinToString(",") { genreName ->
            Genre.getIdByName(genreName).toString()
        },
        studio = studio,
        franchise = franchise,
        censored = censored
    )


fun AnimeStatus.toUi() : AnimeStatusUiModel = when(this) {
    AnimeStatus.ANONS -> AnimeStatusUiModel.ANONS
    AnimeStatus.ONGOING -> AnimeStatusUiModel.ONGOING
    AnimeStatus.RELEASED -> AnimeStatusUiModel.RELEASED
    AnimeStatus.UNKNOWN -> AnimeStatusUiModel.UNKNOWN
}

fun AnimeStatusUiModel.toEntity() : AnimeStatus = when(this) {
    AnimeStatusUiModel.ANONS -> AnimeStatus.ANONS
    AnimeStatusUiModel.ONGOING -> AnimeStatus.ONGOING
    AnimeStatusUiModel.RELEASED -> AnimeStatus.RELEASED
    AnimeStatusUiModel.UNKNOWN -> AnimeStatus.UNKNOWN
}