@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.sharapov.feature_search_screen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.domain_anime.entity.Anime
import com.sharapov.domain_anime.entity.RankingType
import com.sharapov.domain_anime.entity.common.AnimeFilter
import com.sharapov.domain_anime.usecases.anime.list.GetAnimeListUseCase
import com.sharapov.domain_anime.usecases.anime.list.SearchAnimeUseCase
import com.sharapov.domain_anime.usecases.anime.list.UpdateAnimeListUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SearchViewModel.Factory::class)
class SearchViewModel @AssistedInject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase,
    private val updateAnimeListUseCase: UpdateAnimeListUseCase,
    private val searchAnimeUseCase: SearchAnimeUseCase,
    @Assisted("filter") private val filter: AnimeFilter
) : ViewModel() {

    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Initial)
    val state = _state.asStateFlow()

    private var initialList: List<Anime> = listOf()

    private val query = MutableStateFlow("")

    init {
        getAnimeListUseCase(filter)
            .onStart { _state.value = SearchScreenState.Loading }
            .onEach { generalList ->
                if (generalList.isEmpty() && filter is AnimeFilter.All) {
                    val result = updateAnimeListUseCase(rankingType = RankingType.ALL, limit = 100)
                    if (result.isFailure) {
                        _state.value = SearchScreenState.Error(
                            result.exceptionOrNull()?.message ?: "Unable to download anime"
                        )
                        return@onEach
                    }
                }
                initialList = generalList
                _state.update {  prevState ->
                    if (prevState is SearchScreenState.Content) {
                        prevState.copy(query = "", animeList = generalList)
                    } else {
                        prevState
                    }
                }
            }
            .catch { e -> _state.value = SearchScreenState.Error(e.message ?: "Unknown error") }
            .launchIn(viewModelScope)

        query
            .debounce(250)
            .map { it.trim() }
            .distinctUntilChanged()
            .flatMapLatest { q ->
                if (q.isBlank()) {
                    flowOf(initialList)
                } else {
                    searchAnimeUseCase(q, filter)
                        .catch { e ->
                            emit(emptyList())
                            _state.value = SearchScreenState.Error(e.message ?: "Unknown error")
                        }
                }
            }
            .onEach { list ->
                _state.update { prev ->
                    when (prev) {
                        is SearchScreenState.Content -> prev.copy(animeList = list)
                        else -> SearchScreenState.Content(query = query.value, animeList = list)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: SearchScreenCommand) {
        viewModelScope.launch {
            when (command) {
                is SearchScreenCommand.ChangeQuery -> {
                    _state.update { prev ->
                        if (prev is SearchScreenState.Content) {
                            prev.copy(query = command.query)
                        } else {
                            prev
                        }
                    }
                    query.value = command.query
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("filter") filter: AnimeFilter): SearchViewModel
    }
}