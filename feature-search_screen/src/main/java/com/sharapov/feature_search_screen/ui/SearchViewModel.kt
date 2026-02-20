package com.sharapov.feature_search_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.toErrorType
import com.sharapov.core_ui.theme.core.toUiMessage
import com.sharapov.feature_search_screen.domain.entity.AnimeFilter
import com.sharapov.feature_search_screen.domain.usecases.SearchAnimeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = SearchViewModel.Factory::class)
class SearchViewModel @AssistedInject constructor(
    private val searchAnimeUseCase: SearchAnimeUseCase,
    @Assisted("filter") private val filter: AnimeFilter
) : ViewModel() {

    private val _state = MutableStateFlow<SearchScreenState>(LceState.Initial)
    val state = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    private val _filter = MutableStateFlow(filter)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val animePagingFlow: Flow<PagingData<AnimeListItem>> =
        combine(
            _query
            .map { it.trim() }
            .debounce(500)
            .distinctUntilChanged(),
            _filter
        ) { query, filter ->
            query to filter
        }
            .onStart { _state.value = LceState.Loading }
            .onEach { (query, filter) ->
                _state.value = LceState.Content(
                    SearchScreenContent(
                        query = query,
                        filter = filter
                    )
                )
            }
            .flatMapLatest { (query, filter) ->
                searchAnimeUseCase(
                    query = query,
                    limit = 20,
                    filter = filter
                ).flow
            }
            .cachedIn(viewModelScope)

    fun errorLoadStateToLce(throwable: Throwable?) {
        val type = throwable.toErrorType()
        _state.value = LceState.Error(
            type = type,
            message = type.toUiMessage(throwable?.message)
        )
    }

    fun processCommand(command: SearchScreenCommand) {

        when (command) {
            is SearchScreenCommand.ChangeQuery -> {
                _query.value = command.query
                _state.update { prevState ->
                    if (prevState is LceState.Content) {
                        prevState.copy(data = prevState.data.copy(query = command.query))
                    } else {
                        prevState
                    }
                }
            }

            is SearchScreenCommand.ApplyFilter -> {
                _state.update { prevState ->
                    _filter.value = command.filter
                    if (prevState is LceState.Content) {
                        prevState.copy(data = prevState.data.copy(filter = command.filter))
                    } else {
                        prevState
                    }
                }
            }
        }
    }


    @AssistedFactory
    interface Factory {

        fun create(@Assisted("filter") filter: AnimeFilter): SearchViewModel
    }
}
