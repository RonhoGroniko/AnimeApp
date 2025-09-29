package com.sharapov.core_data.mapper

import com.sharapov.core_data.local.dbmodel.AnimeDbModel
import com.sharapov.core_data.local.dbmodel.GenreDbModel
import com.sharapov.core_data.local.dbmodel.StudioDbModel
import com.sharapov.core_data.remote.dto.AnimeResponseDto
import com.sharapov.core_data.remote.dto.DataDto
import com.sharapov.core_data.remote.dto.GenreDto
import com.sharapov.core_data.remote.dto.StudioDto

fun AnimeResponseDto.toDbModels(): List<AnimeDbModel> {
    return data.map { it.toDbModel() }
}

fun DataDto.toDbModel(): AnimeDbModel {
    return AnimeDbModel(
        id = node.id,
        title = node.title,
        imageUrl = node.mainPicture.medium,
        rating = node.mean,
        rank = node.rank,
        genres = node.genres.map { it.toDbModel() },
        createdAt = node.createdAt,
        studios = node.studios.map { it.toDbModel() }
    )
}


fun GenreDto.toDbModel(): GenreDbModel {
    return GenreDbModel(
        name = name
    )
}

fun StudioDto.toDbModel(): StudioDbModel {
    return StudioDbModel(
        name = name
    )
}