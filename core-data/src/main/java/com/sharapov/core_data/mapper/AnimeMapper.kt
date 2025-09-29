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
import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.entity.Genre
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.entity.Studio


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

fun StudioDbModel.toEntity(): Studio {
    return Studio(
        name = name
    )
}