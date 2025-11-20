package com.sharapov.domain_anime.usecases.anime.details

import com.sharapov.domain_anime.entity.details.AnimeWithDetails
import com.sharapov.domain_anime.repository.DetailsAnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeByIdUseCase @Inject constructor(
    private val repository: DetailsAnimeRepository
) {

    operator fun invoke(animeId: Int): Flow<AnimeWithDetails> {
        return repository.getAnimeById(animeId)
    }
}