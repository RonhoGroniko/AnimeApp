package com.sharapov.feature_favorites_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.toLceState
import com.sharapov.domain_anime.usecases.favorites.GetFavoriteAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteAnimeListUseCase: GetFavoriteAnimeListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FavoritesScreenState>(LceState.Initial)
    val state = _state.asStateFlow()

    init {
        getFavoriteAnimeListUseCase()
            .onEach { result ->
                _state.value = result.toLceState { animeList ->
                    FavoritesScreenContent(animeList)
                }
            }
            .launchIn(viewModelScope)
    }
}