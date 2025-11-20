package com.sharapov.domain_anime.usecases.anime.details

import com.sharapov.domain_anime.repository.DetailsAnimeRepository
import javax.inject.Inject

class LoadAnimeByIdUseCase @Inject constructor(
    private val repository: DetailsAnimeRepository
) {

    suspend operator fun invoke(animeId: Int) {
        repository.loadAnimeById(animeId)
    }
}