package com.sharapov.core_domain.usecases

import com.sharapov.core_domain.entity.details.AnimeWithDetails
import com.sharapov.core_domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeByIdUseCase @Inject constructor(
    private val repository: AnimeRepository
) {

    operator fun invoke(animeId: Int): Flow<AnimeWithDetails> {
        return repository.getAnimeById(animeId)
    }
}