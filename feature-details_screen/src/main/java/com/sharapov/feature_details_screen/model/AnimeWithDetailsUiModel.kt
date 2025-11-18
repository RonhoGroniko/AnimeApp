package com.sharapov.feature_details_screen.model

import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.entity.details.AlternativeTitles
import com.sharapov.domain_anime.entity.details.StartSeason
import com.sharapov.domain_anime.entity.details.Statistics

data class AnimeWithDetailsUiModel(
    val alternativeTitles: AlternativeTitles,
    val averageEpisodeDuration: String,
    val background: String,
    val createdAt: String,
    val endDate: String,
    val genres: List<String>,
    val id: Int,
    val mainPicture: String,
    val mean: Double,
    val mediaType: String,
    val nsfw: String,
    val numEpisodes: String,
    val numListUsers: Int,
    val numScoringUsers: Int,
    val pictures: List<String>,
    val popularity: Int,
    val rank: Int,
    val rating: String,
    val recommendations: List<Anime>,
    val relatedAnime: List<RelatedAnimeUiModel>,
//       val relatedManga: List<Any?>
    val source: String,
    val startDate: String,
    val startSeason: StartSeason,
    val statistics: Statistics,
    val status: String,
    val studios: List<String>,
    val synopsis: String,
    val title: String,
    val updatedAt: String,
    val isFavorite: Boolean
)
