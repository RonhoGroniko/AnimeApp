package com.sharapov.database_anime.mapper

import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.database_anime.model.AnimeDbModel

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