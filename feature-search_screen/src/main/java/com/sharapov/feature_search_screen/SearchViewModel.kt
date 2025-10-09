package com.sharapov.feature_search_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_domain.entity.RankingType
import com.sharapov.core_domain.usecases.AnimeFilter
import com.sharapov.core_domain.usecases.GetAnimeListUseCase
import com.sharapov.core_domain.usecases.UpdateAnimeListUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = SearchViewModel.Factory::class)
class SearchViewModel @AssistedInject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase,
    private val updateAnimeListUseCase: UpdateAnimeListUseCase,
    @Assisted("genre") private val genre: String
) : ViewModel() {

    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Initial)
    val state = _state.asStateFlow()

    init {
        val filter = if (genre.isNotBlank()) {
            AnimeFilter.ByGenre(genre)
        } else  {
            AnimeFilter.All
        }
        getAnimeListUseCase(filter)
            .onStart { _state.value = SearchScreenState.Loading }
            .onEach { generalList ->
                if (generalList.isEmpty()) {
                    val r = updateAnimeListUseCase(rankingType = RankingType.ALL, limit = 100)
                    if (r.isFailure) {
                        _state.value = SearchScreenState.Error(
                            r.exceptionOrNull()?.message ?: "Unable to download anime"
                        )
                        return@onEach
                    }
                }
                _state.value = SearchScreenState.Content(query = "", animeList = generalList)
            }
            .catch { e -> _state.value = SearchScreenState.Error(e.message ?: "Unknown error") }
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

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("genre") genre: String): SearchViewModel
    }
}