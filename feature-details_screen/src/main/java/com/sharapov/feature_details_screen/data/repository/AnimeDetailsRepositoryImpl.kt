package com.sharapov.feature_details_screen.data.repository

import com.apollographql.apollo.ApolloClient
import com.sharapov.core_domain.Result
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.database_anime.dao.AnimeDao
import com.sharapov.database_anime.mapper.toDbModel
import com.sharapov.feature_details_screen.data.mapper.toEntity
import com.sharapov.feature_details_screen.domain.entity.AnimeDetails
import com.sharapov.feature_details_screen.domain.repository.AnimeDetailsRepository
import `feature-details_screen`.GetAnimeByIdQuery
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimeDetailsRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val animeDao: AnimeDao
) : AnimeDetailsRepository {

    override fun getAnimeById(animeId: String): Flow<Result<AnimeDetails>> = flow {
        emit(Result.Loading)
        try {
            val data = apolloClient
                .query(GetAnimeByIdQuery(animeId))
                .execute()
                .dataOrThrow()
            emit(Result.Success(data.toEntity()))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(Result.Error(e, e.message))
        }
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