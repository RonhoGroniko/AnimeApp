package com.sharapov.network_anime.mapper

import com.sharapov.domain_anime.entity.filter.AnimeOrder
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.network_anime.SearchAnimeQuery
import com.sharapov.network_anime.type.OrderEnum

fun AnimeOrder.toOrderEnum(): OrderEnum {
    return when (this) {
        AnimeOrder.BY_ID -> OrderEnum.id
        AnimeOrder.BY_ID_DESC -> OrderEnum.id_desc
        AnimeOrder.BY_RANK -> OrderEnum.ranked
        AnimeOrder.BY_TYPE -> OrderEnum.kind
        AnimeOrder.BY_POPULARITY -> OrderEnum.popularity
        AnimeOrder.IN_ALPHABET_ORDER -> OrderEnum.name_
        AnimeOrder.BY_RELEASE_DATE -> OrderEnum.aired_on
        AnimeOrder.BY_NUMBER_OF_EPISODES -> OrderEnum.episodes
        AnimeOrder.BY_STATUS -> OrderEnum.status
        AnimeOrder.BY_RANDOM -> OrderEnum.random
        AnimeOrder.BY_RANKED_RANDOM -> OrderEnum.ranked_random
        AnimeOrder.BY_SHIKIMORI_RANKING -> OrderEnum.ranked_shiki
        AnimeOrder.CREATED_AT -> OrderEnum.created_at
        AnimeOrder.CREATED_AT_DESC -> OrderEnum.created_at_desc
    }
}

fun SearchAnimeQuery.Data.toEntities() : List<AnimeListItem> {
    return this.animes.map { it.animeFields.toEntity() }
}