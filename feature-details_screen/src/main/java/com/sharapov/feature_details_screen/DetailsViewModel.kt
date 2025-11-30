package com.sharapov.feature_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.toLceState
import com.sharapov.domain_anime.usecases.details.GetAnimeByIdUseCase
import com.sharapov.feature_details_screen.mapper.toUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor (
     @Assisted("animeId") private val animeId: Long,
    private val getAnimeByIdUseCase: GetAnimeByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsScreenState>(LceState.Initial)
    val state = _state.asStateFlow()

    init {
        loadAnimeById(animeId)
    }

    private fun loadAnimeById(animeId: Long) {
        viewModelScope.launch {
            getAnimeByIdUseCase(animeId).collect { result ->
                _state.value = result.toLceState { anime ->
                    DetailsScreenContent(anime.toUi())
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("animeId") animeId: Long): DetailsViewModel
    }
}