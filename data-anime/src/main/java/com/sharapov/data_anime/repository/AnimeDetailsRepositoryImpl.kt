package com.sharapov.data_anime.repository

import com.apollographql.apollo.ApolloClient
import com.sharapov.core_domain.Result
import com.sharapov.domain_anime.entity.details.AnimeDetails
import com.sharapov.domain_anime.repository.AnimeDetailsRepository
import com.sharapov.network_anime.GetAnimeByIdQuery
import com.sharapov.core_network.mapper.toEntity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeDetailsRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
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
}