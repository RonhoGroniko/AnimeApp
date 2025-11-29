package com.sharapov.feature_main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.SectionState
import com.sharapov.core_ui.theme.core.stateWithSections
import com.sharapov.core_ui.theme.core.toSectionState
import com.sharapov.domain_anime.entity.AnimeListItem
import com.sharapov.domain_anime.entity.AnimeStatus
import com.sharapov.domain_anime.usecases.GetAnimeListUseCase
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
            getAnimeListUseCase(animeStatus = AnimeStatus.ANONS).collect { result ->
                _upcomingAnimeSectionState.value = result.toSectionState()
            }
        }
    }

    private fun loadOngoingAnime() {
        viewModelScope.launch {
            getAnimeListUseCase(animeStatus = AnimeStatus.ONGOING).collect { result ->
                _ongoingAnimeSectionState.value = result.toSectionState()
            }
        }
    }

    private fun loadReleasedAnime() {
        viewModelScope.launch {
            getAnimeListUseCase(animeStatus = AnimeStatus.RELEASED).collect { result ->
                _releasedAnimeSectionState.value = result.toSectionState()
            }
        }
    }
}