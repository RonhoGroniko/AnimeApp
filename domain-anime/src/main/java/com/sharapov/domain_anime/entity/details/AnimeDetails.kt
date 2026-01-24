package com.sharapov.domain_anime.entity.details

import com.sharapov.domain_anime.entity.AnimeKind
import com.sharapov.domain_anime.entity.AnimeRating
import com.sharapov.domain_anime.entity.AnimeStatus
import com.sharapov.domain_anime.entity.Genre
import com.sharapov.domain_anime.entity.Studio

data class AnimeDetails(
    val id: Long,
    val name: String,
    val russianName: String,
    val englishName: String,
    val japaneseName: String,
    val kind: AnimeKind,
    val rating: AnimeRating,
    val score: Double,
    val status: AnimeStatus,
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
    val genres: List<Genre>,
    val studios: List<Studio>,
    val chronology: List<AnimeChronology>,
    val characters: List<Character>,
    val relatedAnime: List<RelatedAnime>,
    val videos: List<Video>,
    val screenshotsUrls: List<Screenshot>,
    val scoreStats: List<ScoreStats>,
    val statusStats: List<StatusStats>,
    val description: String
)