package com.sharapov.database_anime

import androidx.room.withTransaction
import com.sharapov.database_anime.dao.AnimeCoreDao
import com.sharapov.database_anime.dao.AnimeDetailsDao
import com.sharapov.database_anime.model.details.AlternativeTitleSynonymDbModel
import com.sharapov.database_anime.model.details.AlternativeTitlesDbModel
import com.sharapov.database_anime.model.details.AnimeDetailsDbModel
import com.sharapov.database_anime.model.details.PictureDbModel
import com.sharapov.database_anime.model.details.RecommendationsDbModel
import com.sharapov.database_anime.model.details.RelatedAnimeDbModel
import com.sharapov.database_anime.model.details.StartSeasonDbModel
import com.sharapov.database_anime.model.details.StatisticsDbModel
import com.sharapov.database_anime.model.list.AnimeDbModel
import com.sharapov.database_anime.model.list.AnimeGenreCrossRef
import com.sharapov.database_anime.model.list.AnimeListItemDbModel
import com.sharapov.database_anime.model.list.AnimeRankingTypeCrossRef
import com.sharapov.database_anime.model.list.AnimeStudioCrossRef
import com.sharapov.database_anime.model.list.GenreDbModel
import javax.inject.Inject

class AnimeLocalDataSource @Inject constructor (
    private val db: AnimeDatabase,
    private val coreDao: AnimeCoreDao,
    private val detailsDao: AnimeDetailsDao,
) {

    suspend fun upsertFullAnime(animeFullDbModelList: List<AnimeListItemDbModel>) =
        db.withTransaction {
            coreDao.upsertAnime(animeFullDbModelList.map { it.anime })

            coreDao.upsertGenres(
                animeFullDbModelList.flatMap { it.genres }.distinctBy { it.id }
            )

            coreDao.upsertStudios(
                animeFullDbModelList.flatMap { it.studios }.distinctBy { it.id }
            )

            val typesByName = animeFullDbModelList
                .flatMap { it.rankingTypes }
                .distinctBy { it.name }

            coreDao.upsertRankingTypes(typesByName)

            val genreRefs = animeFullDbModelList.flatMap { full ->
                full.genres.map { g ->
                    AnimeGenreCrossRef(full.anime.id, g.id)
                }
            }

            val studioRefs = animeFullDbModelList.flatMap { full ->
                full.studios.map { s ->
                    AnimeStudioCrossRef(full.anime.id, s.id)
                }
            }

            val typesFromDb = coreDao.getRankingTypesByNames(typesByName.map { it.name })
            val typeIdByName = typesFromDb.associateBy({ it.name }, { it.id })

            val rankingTypeRefs = animeFullDbModelList.flatMap { full ->
                full.rankingTypes.mapNotNull { r ->
                    val rtId = typeIdByName[r.name] ?: return@mapNotNull null
                    AnimeRankingTypeCrossRef(full.anime.id, rtId)
                }
            }

            if (genreRefs.isNotEmpty()) coreDao.insertAnimeGenreRefs(genreRefs)
            if (studioRefs.isNotEmpty()) coreDao.insertAnimeStudioRefs(studioRefs)
            if (rankingTypeRefs.isNotEmpty()) coreDao.insertRankingTypes(rankingTypeRefs)
        }

    suspend fun upsertDetailsBundle(
        details: AnimeDetailsDbModel,
        statistics: StatisticsDbModel,
        startSeason: StartSeasonDbModel,
        alternativeTitles: AlternativeTitlesDbModel,
        synonyms: List<AlternativeTitleSynonymDbModel>,
        recommendedAnime: List<AnimeDbModel>,
        recommendationsLinks: List<RecommendationsDbModel>,
        relatedAnime: List<AnimeDbModel>,
        relatedLinks: List<RelatedAnimeDbModel>,
        pictures: List<PictureDbModel>,
        genres: List<GenreDbModel>
    ) = db.withTransaction {

        val realBase = (recommendedAnime + relatedAnime).distinctBy { it.id }
        if (realBase.isNotEmpty()) coreDao.upsertAnime(realBase)

        detailsDao.upsertAnimeDetails(listOf(details))
        detailsDao.upsertStatistics(listOf(statistics))
        detailsDao.upsertStartSeasons(listOf(startSeason))
        detailsDao.upsertAlternativeTitles(listOf(alternativeTitles))

        if (genres.isNotEmpty()) {
            coreDao.upsertGenres(genres.distinctBy { it.id })

            detailsDao.clearAnimeGenreRefsForAnime(details.id)

            val genreRefs = genres.map { g ->
                AnimeGenreCrossRef(details.id, g.id)
            }
            coreDao.insertAnimeGenreRefs(genreRefs)
        }

        detailsDao.clearAlternativeTitleSynonyms(details.id)
        if (synonyms.isNotEmpty()) detailsDao.insertAlternativeTitleSynonyms(synonyms)

        detailsDao.clearRecommendations(details.id)
        if (recommendationsLinks.isNotEmpty()) detailsDao.insertRecommendations(recommendationsLinks)

        detailsDao.clearRelatedAnime(details.id)
        if (relatedLinks.isNotEmpty()) detailsDao.insertRelatedAnime(relatedLinks)

        detailsDao.clearPictures(details.id)
        if (pictures.isNotEmpty()) detailsDao.insertPictures(pictures)
    }
}

