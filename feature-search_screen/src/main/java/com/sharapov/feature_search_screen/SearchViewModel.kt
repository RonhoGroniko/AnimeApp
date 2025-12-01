package com.sharapov.feature_search_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.toLceState
import com.sharapov.domain_anime.entity.AnimeStatus
import com.sharapov.domain_anime.usecases.list.GetAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<SearchScreenState>(LceState.Initial)
    val state = _state.asStateFlow()

    init {
        loadTestList()
    }

    fun processCommand(command: SearchScreenCommand) {
        when (command) {
            is SearchScreenCommand.ChangeQuery -> {
                _state.update { prevState ->
                    if (prevState is LceState.Content) {
                        Log.d("SearchViewModel", command.query)
                        prevState.copy(
                            SearchScreenContent(
                                query = command.query,
                                animeList = listOf()
                            )
                        )
                    } else {
                        prevState
                    }
                }
            }
        }
    }

    private fun loadTestList() {
        viewModelScope.launch {
            getAnimeListUseCase(
                animeStatus = AnimeStatus.RELEASED,
                limit = 50
            ).collect { result ->
                _state.value = result.toLceState { animeList ->
                    SearchScreenContent(
                        animeList = animeList,
                        query = ""
                    )
                }
            }
        }
    }
}
