package com.sharapov.domain_anime.usecases.list

import com.sharapov.domain_anime.entity.AnimeStatus
import com.sharapov.domain_anime.repository.AnimeListRepository
import javax.inject.Inject

class GetAnimeListUseCase @Inject constructor(
    private val animeListRepository: AnimeListRepository
) {

    operator fun invoke(animeStatus: AnimeStatus = AnimeStatus.RELEASED, limit: Int = 8) = animeListRepository.getAnimeList(animeStatus, limit)
}