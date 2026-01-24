package com.sharapov.database_anime.mapper

import com.sharapov.database_anime.model.AnimeDbModel
import com.sharapov.domain_anime.entity.list.AnimeListItem

fun AnimeDbModel.toEntity(): AnimeListItem {
    return AnimeListItem(
        id = id,
        name = name,
        score = score,
        imageUrl = imageUrl
    )
}

fun List<AnimeDbModel>.toEntities(): List<AnimeListItem> {
    return map { it.toEntity() }
}

fun AnimeListItem.toDbModel(): AnimeDbModel {
    return AnimeDbModel(
        id = id,
        name = name,
        score = score,
        imageUrl = imageUrl,
        createdAt = System.currentTimeMillis(),
    )
}