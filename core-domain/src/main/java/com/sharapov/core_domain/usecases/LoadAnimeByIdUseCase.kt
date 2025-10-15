package com.sharapov.core_domain.usecases

import com.sharapov.core_domain.repository.AnimeRepository
import javax.inject.Inject

class LoadAnimeByIdUseCase @Inject constructor(
    private val repository: AnimeRepository
) {

    suspend operator fun invoke(animeId: Int) {
        repository.loadAnimeById(animeId)
    }
}