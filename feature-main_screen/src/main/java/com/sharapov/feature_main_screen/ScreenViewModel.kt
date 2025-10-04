package com.sharapov.feature_main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.usecases.GetAnimeListUseCase
import com.sharapov.core_domain.usecases.UpdateAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenViewModel @Inject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase,
    private val updateAnimeListUseCase: UpdateAnimeListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Initial)
    val state = _state.asStateFlow()

    init {
        val upcomingFlow = getAnimeListUseCase(RankingType.UPCOMING)
            .distinctUntilChanged()

        val airingFlow = getAnimeListUseCase(RankingType.AIRING)
            .distinctUntilChanged()

        combine(upcomingFlow, airingFlow) { upcoming, airing ->
            upcoming to airing
        }
            .onStart { _state.value = MainScreenState.Loading }
            .onEach { (upcoming, airing) ->
                if (upcoming.isEmpty()) {
                    val result = updateAnimeListUseCase(RankingType.UPCOMING, 40)
                    if (result.isFailure) {
                        _state.value = MainScreenState.Error(
                            result.exceptionOrNull()?.message
                                ?: "Unable to download Upcoming anime"
                        )
                        return@onEach
                    }
                }
                if (airing.isEmpty()) {
                    val result = updateAnimeListUseCase(RankingType.AIRING, 40)
                    if (result.isFailure) {
                        _state.value = MainScreenState.Error(
                            result.exceptionOrNull()?.message
                                ?: "Unable to download Airing anime"
                        )
                        return@onEach
                    }
                }
                _state.value = MainScreenState.Content(
                    upcomingList = upcoming,
                    airingList = airing
                )
            }
            .catch { e ->
                _state.value = MainScreenState.Error(e.message ?: "Unknown error")
            }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: MainScreenCommand) {
        viewModelScope.launch {
            when (command) {
                MainScreenCommand.RefreshData -> {
                    Log.d("ScreenViewModel", command.toString())
                    _state.value = MainScreenState.Loading
                    val upcomingResult = updateAnimeListUseCase(RankingType.UPCOMING, 40)
                    if (upcomingResult.isFailure) {
                        _state.value = MainScreenState.Error(
                            upcomingResult.exceptionOrNull()?.message
                                ?: "Unable to download Upcoming anime"
                        )
                    }
                    val airingResult = updateAnimeListUseCase(RankingType.AIRING, 40)
                    if (airingResult.isFailure) {
                        _state.value = MainScreenState.Error(
                            airingResult.exceptionOrNull()?.message
                                ?: "Unable to download Airing anime"
                        )
                    }
                }
            }
        }
    }
}