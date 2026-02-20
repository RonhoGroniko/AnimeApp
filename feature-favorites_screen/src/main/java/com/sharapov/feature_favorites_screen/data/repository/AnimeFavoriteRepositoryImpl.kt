package com.sharapov.feature_favorites_screen.data.repository

import com.sharapov.core_domain.Result
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.database_anime.dao.AnimeDao
import com.sharapov.database_anime.mapper.toEntities
import com.sharapov.feature_favorites_screen.domain.repository.AnimeFavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class AnimeFavoriteRepositoryImpl @Inject constructor(
    private val animeDao: AnimeDao
) : AnimeFavoriteRepository {

    override fun getFavoriteAnimeList(): Flow<Result<List<AnimeListItem>>> =
        animeDao.getFavoriteAnimeList()
            .map { animeList ->
                Result.Success(animeList.toEntities()) as Result<List<AnimeListItem>>
            }
            .onStart {
                emit(Result.Loading)
            }
            .catch { e ->
                emit(
                    Result.Error(
                        exception = e,
                        message = e.message
                    )
                )
            }
}