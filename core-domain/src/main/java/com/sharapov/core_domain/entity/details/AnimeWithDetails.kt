package com.sharapov.core_domain.entity.details

import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.entity.Genre
import com.sharapov.core_domain.entity.Studio

data class AnimeWithDetails(

    val alternativeTitles: AlternativeTitles,
    val averageEpisodeDuration: Int,
    val background: String,
    val createdAt: String,
    val endDate: String,
    val genres: List<Genre>,
    val id: Int,
    val mainPicture: String,
    val mean: Double,
    val mediaType: MediaType,
    val nsfw: String,
    val numEpisodes: Int,
    val numListUsers: Int,
    val numScoringUsers: Int,
    val pictures: List<String>,
    val popularity: Int,
    val rank: Int,
    val rating: AgeRating,
    val recommendations: List<Anime>,
    val relatedAnime: List<RelatedAnime>,
//       val relatedManga: List<Any?>
    val source: Source,
    val startDate: String,
    val startSeason: StartSeason,
    val statistics: Statistics,
    val status: Status,
    val studios: List<Studio>,
    val synopsis: String,
    val title: String,
    val updatedAt: String,
    val isFavorite: Boolean
)
