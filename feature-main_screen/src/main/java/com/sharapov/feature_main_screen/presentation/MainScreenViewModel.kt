package com.sharapov.feature_main_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.domain_anime.entity.RankingType
import com.sharapov.domain_anime.entity.common.AnimeFilter
import com.sharapov.domain_anime.usecases.anime.list.GetAnimeListUseCase
import com.sharapov.domain_anime.usecases.anime.list.UpdateAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase,
    private val updateAnimeListUseCase: UpdateAnimeListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Initial)
    val state = _state.asStateFlow()

    init {
        val upcomingFlow = getAnimeListUseCase(AnimeFilter.ByRankingType(RankingType.UPCOMING))
        val airingFlow = getAnimeListUseCase(AnimeFilter.ByRankingType(RankingType.AIRING))
        val popularityFlow = getAnimeListUseCase(AnimeFilter.ByRankingType(RankingType.BY_POPULARITY))

        combine(upcomingFlow, airingFlow, popularityFlow) { upcoming, airing, popularity ->
            Triple(upcoming, airing, popularity)
        }
            .onStart { _state.value = MainScreenState.Loading }
            .onEach { (upcoming, airing, popularity) ->
                if (upcoming.isEmpty()) {
                    val r = updateAnimeListUseCase(RankingType.UPCOMING, 40)
                    if (r.isFailure) {
                        _state.value = MainScreenState.Error(
                            r.exceptionOrNull()?.message ?: "Unable to download Upcoming anime"
                        )
                        return@onEach
                    }
                }
                if (airing.isEmpty()) {
                    val r = updateAnimeListUseCase(RankingType.AIRING, 40)
                    if (r.isFailure) {
                        _state.value = MainScreenState.Error(
                            r.exceptionOrNull()?.message ?: "Unable to download Airing anime"
                        )
                        return@onEach
                    }
                }
                if (popularity.isEmpty()) {
                    val r = updateAnimeListUseCase(RankingType.BY_POPULARITY, 40)
                    if (r.isFailure) {
                        _state.value = MainScreenState.Error(
                            r.exceptionOrNull()?.message ?: "Unable to download Popular anime"
                        )
                        return@onEach
                    }
                }

                _state.value = MainScreenState.Content(
                    upcomingList = upcoming,
                    airingList = airing,
                    popularList = popularity
                )
            }
            .catch { e -> _state.value = MainScreenState.Error(e.message ?: "Unknown error") }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: MainScreenCommand) {
        viewModelScope.launch {
            when (command) {
                MainScreenCommand.RefreshData -> {
                    _state.value = MainScreenState.Loading
                    val up = updateAnimeListUseCase(RankingType.UPCOMING, 40)
                    if (up.isFailure) {
                        _state.value = MainScreenState.Error(
                            up.exceptionOrNull()?.message ?: "Unable to download Upcoming anime"
                        )
                    }
                    val air = updateAnimeListUseCase(RankingType.AIRING, 40)
                    if (air.isFailure) {
                        _state.value = MainScreenState.Error(
                            air.exceptionOrNull()?.message ?: "Unable to download Airing anime"
                        )
                    }
                    val pop = updateAnimeListUseCase(RankingType.BY_POPULARITY, 40)
                    if (pop.isFailure) {
                        _state.value = MainScreenState.Error(
                            pop.exceptionOrNull()?.message ?: "Unable to download Popular anime"
                        )
                    }
                }
            }
        }
    }
}