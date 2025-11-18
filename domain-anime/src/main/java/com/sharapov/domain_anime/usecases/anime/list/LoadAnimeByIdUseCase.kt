package com.sharapov.domain_anime.usecases.anime.list

import com.sharapov.domain_anime.repository.AnimeRepository
import javax.inject.Inject

class LoadAnimeByIdUseCase @Inject constructor(
    private val repository: AnimeRepository
) {

    suspend operator fun invoke(animeId: Int) {
        repository.loadAnimeById(animeId)
    }
}