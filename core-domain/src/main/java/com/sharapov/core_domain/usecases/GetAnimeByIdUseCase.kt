package com.sharapov.core_domain.usecases

import com.sharapov.core_domain.entity.details.AnimeWithDetails
import com.sharapov.core_domain.repository.AnimeRepository
import javax.inject.Inject

class GetAnimeByIdUseCase @Inject constructor(
    private val repository: AnimeRepository
) {

    suspend operator fun invoke(animeId: Int): AnimeWithDetails {
        return repository.getAnimeById(animeId)
    }
}