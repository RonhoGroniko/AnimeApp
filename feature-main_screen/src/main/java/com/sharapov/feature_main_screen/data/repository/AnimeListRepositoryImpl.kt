package com.sharapov.feature_main_screen.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.sharapov.core_domain.entity.AnimeStatus
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.feature_main_screen.domain.repository.AnimeListRepository
import `feature-main_screen`.GetAnimeListQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import com.sharapov.core_domain.Result
import com.sharapov.feature_main_screen.data.mapper.toEntity

class AnimeListRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : AnimeListRepository {

    override fun getAnimeList(
        animeStatus: AnimeStatus,
        limit: Int
    ): Flow<Result<List<AnimeListItem>>> = flow {
        emit(Result.Loading)
        try {
            val data = apolloClient
                .query(
                    GetAnimeListQuery(
                        status = Optional.present(animeStatus.value),
                        limit = Optional.present(limit)
                    )
                )
                .execute()
                .dataOrThrow()
            emit(Result.Success(data.toEntity()))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(
                Result.Error(
                    exception = e,
                    message = e.message
                )
            )
        }
    }.flowOn(Dispatchers.IO)
}