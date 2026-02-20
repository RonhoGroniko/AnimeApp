package com.sharapov.feature_details_screen.ui.model

import com.sharapov.feature_details_screen.domain.entity.AnimeChronology
import com.sharapov.feature_details_screen.domain.entity.Character
import com.sharapov.feature_details_screen.domain.entity.RelatedAnime
import com.sharapov.feature_details_screen.domain.entity.ScoreStats
import com.sharapov.feature_details_screen.domain.entity.Screenshot
import com.sharapov.feature_details_screen.domain.entity.StatusStats
import com.sharapov.feature_details_screen.domain.entity.Video


data class AnimeDetailsUiModel(
    val id: Long,
    val name: String,
    val isFavorite: Boolean,
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
