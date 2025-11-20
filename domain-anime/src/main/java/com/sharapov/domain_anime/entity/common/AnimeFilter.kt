package com.sharapov.domain_anime.entity.common

import com.sharapov.domain_anime.entity.RankingType

sealed interface AnimeFilter {
    data object All : AnimeFilter
    data object Favorites: AnimeFilter
    data class ByGenre(val genre: String) : AnimeFilter
    data class ByRankingType(val rankingType: RankingType) : AnimeFilter
}