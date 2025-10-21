package com.sharapov.core_navigation.mapper

import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.usecases.AnimeFilter
import com.sharapov.core_navigation.NavItemAnimeFilter

fun AnimeFilter.toNavItem(): NavItemAnimeFilter {
    return when(this) {
        AnimeFilter.All -> NavItemAnimeFilter.All
        is AnimeFilter.ByGenre -> NavItemAnimeFilter.ByGenre(genre)
        is AnimeFilter.ByRankingType -> NavItemAnimeFilter.ByRankingType(rankingType.name)
        AnimeFilter.Favorites -> NavItemAnimeFilter.Favorites
    }
}

fun NavItemAnimeFilter.toEntity(): AnimeFilter {
    return when(this) {
        NavItemAnimeFilter.All -> AnimeFilter.All
        is NavItemAnimeFilter.ByGenre -> AnimeFilter.ByGenre(genre)
        is NavItemAnimeFilter.ByRankingType -> AnimeFilter.ByRankingType(RankingType.valueOf(rankingTypeName))
        NavItemAnimeFilter.Favorites -> AnimeFilter.Favorites
    }
}