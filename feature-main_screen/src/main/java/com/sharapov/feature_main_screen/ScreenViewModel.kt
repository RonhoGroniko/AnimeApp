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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenViewModel @Inject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase,
    private val updateAnimeListUseCase: UpdateAnimeListUseCase
): ViewModel() {

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Initial)
    val state = _state.asStateFlow()

    init {
        getAnimeListUseCase(RankingType.UPCOMING)
            .onStart { _state.value = MainScreenState.Loading }
            .onEach { animeList ->
                if (animeList.isEmpty()) {
                    updateAnimeListUseCase(RankingType.UPCOMING)
                }
                _state.value = MainScreenState.Content(animeList)
            }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: MainScreenCommand) {
        viewModelScope.launch {
            when(command) {
                MainScreenCommand.RefreshData -> {
                    Log.d("ScreenViewModel", command.toString())
                    _state.value = MainScreenState.Loading
                    updateAnimeListUseCase(RankingType.UPCOMING)
                }
            }
        }
    }
}