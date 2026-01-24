package com.sharapov.data_anime.repository

import com.sharapov.core_domain.Result
import com.sharapov.database_anime.dao.AnimeDao
import com.sharapov.database_anime.mapper.toDbModel
import com.sharapov.database_anime.mapper.toEntities
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.domain_anime.repository.AnimeFavoriteRepository
import kotlinx.coroutines.CancellationException
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

    override suspend fun changeFavoriteStatus(
        anime: AnimeListItem,
        makeFavorite: Boolean
    ): Result<Unit> {
        try {
            if (makeFavorite) {
                animeDao.addFavoriteAnime(anime.toDbModel())
            } else {
                animeDao.removeFromFavorites(anime.id)
            }
            return Result.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Result.Error(
                exception = e,
                message = e.message
            )
        }
    }

    override fun getFavoriteStatus(animeId: Long): Flow<Result<Boolean>> =
        animeDao.getFavoriteStatus(animeId)
            .map { Result.Success(it) as Result<Boolean> }
            .catch { e ->
                emit(
                    Result.Error(
                        exception = e,
                        message = e.message
                    )
                )
            }
}