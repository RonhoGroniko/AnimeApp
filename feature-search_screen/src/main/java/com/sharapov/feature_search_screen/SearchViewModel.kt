package com.sharapov.feature_search_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.usecases.GetAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Initial)
    val state = _state.asStateFlow()

    init {
        getAnimeListUseCase(RankingType.ALL)
            .onStart { _state.value = SearchScreenState.Loading }
            .onEach { _state.value = SearchScreenState.Content(query = "", animeList = it) }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: SearchScreenCommand) {
        when (command) {
            is SearchScreenCommand.ChangeQuery -> {
                _state.update { prevState ->
                    if (prevState is SearchScreenState.Content) {
                        prevState.copy(query = command.query)
                    } else {
                        prevState
                    }
                }
            }

            is SearchScreenCommand.Search -> {
                _state.update { prevState ->
                    if (prevState is SearchScreenState.Content) {
                        // TODO SEARCH AND VALIDATE INPUT
                        Log.d("TEST", command.query)
                        prevState.copy(query = "")
                    } else {
                        prevState
                    }
                }
            }
        }
    }
}