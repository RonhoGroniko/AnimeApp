package com.sharapov.network_anime.mapper

import com.sharapov.domain_anime.entity.AnimeKind
import com.sharapov.domain_anime.entity.AnimeRating
import com.sharapov.domain_anime.entity.AnimeStatus
import com.sharapov.domain_anime.entity.filter.AnimeOrder
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.network_anime.SearchAnimeQuery
import com.sharapov.network_anime.type.AnimeKindEnum
import com.sharapov.network_anime.type.AnimeRatingEnum
import com.sharapov.network_anime.type.AnimeStatusEnum
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

fun AnimeStatus.toAnimeStatusEnum(): AnimeStatusEnum {
    return when(this) {
        AnimeStatus.ANONS -> AnimeStatusEnum.anons
        AnimeStatus.ONGOING -> AnimeStatusEnum.ongoing
        AnimeStatus.RELEASED -> AnimeStatusEnum.released
        else -> AnimeStatusEnum.UNKNOWN__
    }
}

fun AnimeKind.toAnimeKindEnum(): AnimeKindEnum {
    return when(this) {
        AnimeKind.TV -> AnimeKindEnum.tv
        AnimeKind.MOVIE -> AnimeKindEnum.movie
        AnimeKind.OVA -> AnimeKindEnum.ova
        AnimeKind.ONA -> AnimeKindEnum.ona
        AnimeKind.SPECIAL -> AnimeKindEnum.special
        AnimeKind.TV_SPECIAL -> AnimeKindEnum.tv_special
        AnimeKind.MUSIC -> AnimeKindEnum.music
        AnimeKind.PV -> AnimeKindEnum.pv
        AnimeKind.CM -> AnimeKindEnum.cm
        AnimeKind.UNKNOWN -> AnimeKindEnum.UNKNOWN__
    }
}

fun AnimeRating.toAnimeRatingEnum() : AnimeRatingEnum {
    return when(this) {
        AnimeRating.NONE -> AnimeRatingEnum.none
        AnimeRating.G -> AnimeRatingEnum.g
        AnimeRating.PG -> AnimeRatingEnum.pg
        AnimeRating.PG_13 -> AnimeRatingEnum.pg_13
        AnimeRating.R -> AnimeRatingEnum.r
        AnimeRating.R_PLUS -> AnimeRatingEnum.r_plus
        AnimeRating.RX -> AnimeRatingEnum.rx
        AnimeRating.UNKNOWN -> AnimeRatingEnum.UNKNOWN__
    }
}