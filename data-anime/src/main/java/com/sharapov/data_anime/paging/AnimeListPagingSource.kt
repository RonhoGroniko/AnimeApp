package com.sharapov.data_anime.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.sharapov.domain_anime.entity.filter.AnimeFilter
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.network_anime.SearchAnimeQuery
import com.sharapov.network_anime.mapper.toAnimeKindEnum
import com.sharapov.network_anime.mapper.toAnimeRatingEnum
import com.sharapov.network_anime.mapper.toAnimeStatusEnum
import com.sharapov.network_anime.mapper.toEntities
import com.sharapov.network_anime.mapper.toOrderEnum
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AnimeListPagingSource @AssistedInject constructor(
    private val apolloClient: ApolloClient,
    @Assisted("query") private val query: String,
    @Assisted("limit") private val limit: Int,
    @Assisted("filter") private val filter: AnimeFilter,
) : PagingSource<Int, AnimeListItem>() {


    override fun getRefreshKey(state: PagingState<Int, AnimeListItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeListItem> {
        val page = params.key ?: 1
        val pageSize = limit
        try {
            val data = apolloClient.query(
                SearchAnimeQuery(
                    query = query,
                    limit = pageSize,
                    page = page,
                    order = filter.order.toOrderEnum(),
                    status = filter.status?.let { Optional.present(it.toAnimeStatusEnum().rawValue) } ?: Optional.absent(),
                    kind = filter.kind?.let { Optional.present(it.toAnimeKindEnum().rawValue) } ?: Optional.absent(),
                    season = filter.season?.let { Optional.present(it) } ?: Optional.absent(),
                    rating = filter.rating?.let { Optional.present(it.toAnimeRatingEnum().rawValue) } ?: Optional.absent(),
                    origin = filter.origin?.let { Optional.present(it.value) } ?: Optional.absent(),
                    genres = filter.genre?.let { Optional.present(it) } ?: Optional.absent(),
                    studios = filter.studio?.let { Optional.present(it) } ?: Optional.absent(),
                    franchise = filter.franchise?.let { Optional.present(it) } ?: Optional.absent(),
                    censored = filter.censored.let { Optional.present(it) },
                )
            )
                .execute()
                .dataOrThrow()
            val nextKey = if (data.animes.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            Log.d("PagingSource Load", "Page: $page, limit: $limit, ids: ${data.toEntities().take(5).map { it.id }}")
            return LoadResult.Page(data.toEntities(), prevKey, nextKey)
        } catch (e: Exception) {
            Log.d("error", e.toString())
            return LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("query") query: String,
            @Assisted("filter") filter: AnimeFilter,
            @Assisted("limit") limit: Int
        ): AnimeListPagingSource
    }
}