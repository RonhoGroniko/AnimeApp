package com.sharapov.feature_recommendations_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.toLceState
import com.sharapov.feature_recommendations_screen.domain.usecases.GetRecommendationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val getRecommendationsUseCase: GetRecommendationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RecommendScreenState>(LceState.Initial)
    val state = _state.asStateFlow()

    init {
        getRecommendationsUseCase()
            .onEach { result ->
            _state.value = result.toLceState { animeList ->
                RecommendScreenContent(animeList)
            }
        }
            .launchIn(viewModelScope)
    }
}