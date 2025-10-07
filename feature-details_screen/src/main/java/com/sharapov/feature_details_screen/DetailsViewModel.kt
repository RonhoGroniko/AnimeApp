package com.sharapov.feature_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_data.remote.DataException
import com.sharapov.core_domain.usecases.GetAnimeByIdUseCase
import com.sharapov.feature_details_screen.mapper.toUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted("id") id: Int,
    private val getAnimeByIdUseCase: GetAnimeByIdUseCase
): ViewModel() {

    private val _state = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Initial)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val anime = getAnimeByIdUseCase(id)
                _state.value = DetailsScreenState.Content(anime.toUiModel())
            } catch (e: DataException) {
                _state.value = DetailsScreenState.Error(e.cause?.message ?: "Unknown message")
            }
        }
    }

    fun processCommand(command: DetailsScreenCommand) {

    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("id") id: Int): DetailsViewModel
    }
}