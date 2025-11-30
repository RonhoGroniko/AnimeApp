package com.sharapov.feature_details_screen.model

import com.sharapov.domain_anime.entity.details.AnimeChronology
import com.sharapov.domain_anime.entity.details.Character
import com.sharapov.domain_anime.entity.details.RelatedAnime
import com.sharapov.domain_anime.entity.details.ScoreStats
import com.sharapov.domain_anime.entity.details.Screenshot
import com.sharapov.domain_anime.entity.details.StatusStats
import com.sharapov.domain_anime.entity.details.Video

data class AnimeDetailsUiModel(
    val id: Long,
    val name: String,
    val russianName: String,
    val englishName: String,
    val japaneseName: String,
    val kind: String,
    val rating: String,
    val score: Double,
    val status: String,
    val episodes: Int,
    val episodesAired: Int,
    val duration: Int, // minutes
    val franchise: String,
    val airedOnDate: String, // ISO8601Date
    val releasedOnDate: String, // ISO8601Date
    val season: String,
    val imageUrl: String,
    val createdAt: String, // ISO8601DateTime
    val nextEpisodeAt: String, // ISO8601DateTime
    val genres: List<String>,
    val studios: List<String>,
    val chronology: List<AnimeChronology>,
    val characters: List<Character>,
    val relatedAnime: List<RelatedAnime>,
    val videos: List<Video>,
    val screenshotsUrls: List<Screenshot>,
    val scoreStats: List<ScoreStats>,
    val statusStats: List<StatusStats>,
    val description: String
)
