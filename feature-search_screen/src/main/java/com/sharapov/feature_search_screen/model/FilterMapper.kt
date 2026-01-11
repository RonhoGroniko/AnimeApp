package com.sharapov.feature_search_screen.model

import com.sharapov.domain_anime.entity.filter.AnimeFilter
import com.sharapov.domain_anime.entity.filter.genre.Genre

fun AnimeFilter.toUi(): AnimeFilterUiModel =
    AnimeFilterUiModel(
        order = order,
        kind = kind,
        status = status,
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
        status = status,
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
