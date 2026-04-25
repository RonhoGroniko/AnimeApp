package com.sharapov.feature_main_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_domain.entity.AnimeStatus
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.SectionState
import com.sharapov.core_ui.theme.core.stateWithSections
import com.sharapov.core_ui.theme.core.toSectionState
import com.sharapov.feature_main_screen.domain.usecases.GetAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase
) : ViewModel() {

    private val _upcomingAnimeSectionState = MutableStateFlow<SectionState<List<AnimeListItem>>>(
        SectionState.Initial
    )

    private val _ongoingAnimeSectionState = MutableStateFlow<SectionState<List<AnimeListItem>>>(
        SectionState.Initial
    )

    private val _releasedAnimeSectionState = MutableStateFlow<SectionState<List<AnimeListItem>>>(
        SectionState.Initial
    )

    val state: StateFlow<MainScreenState> = combine(
        _upcomingAnimeSectionState,
        _ongoingAnimeSectionState,
        _releasedAnimeSectionState
    ) { upcoming, airing, released ->
        stateWithSections(listOf(upcoming, airing, released)) {
            MainScreenContent(upcoming, airing, released)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = LceState.Initial
    )

    init {
        loadUpcomingAnime()
        loadOngoingAnime()
        loadReleasedAnime()
    }

    private fun loadUpcomingAnime() {
        viewModelScope.launch {
            getAnimeListUseCase(animeStatus = AnimeStatus.ANONS, limit = 8).collect { result ->
                _upcomingAnimeSectionState.value = result.toSectionState()
            }
        }
    }

    private fun loadOngoingAnime() {
        viewModelScope.launch {
            getAnimeListUseCase(animeStatus = AnimeStatus.ONGOING, limit = 8).collect { result ->
                _ongoingAnimeSectionState.value = result.toSectionState()
            }
        }
    }

    private fun loadReleasedAnime() {
        viewModelScope.launch {
            getAnimeListUseCase(animeStatus = AnimeStatus.RELEASED, limit = 8).collect { result ->
                _releasedAnimeSectionState.value = result.toSectionState()
            }
        }
    }
}