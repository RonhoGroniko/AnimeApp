package com.sharapov.feature_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_domain.usecases.ChangeAnimeFavoriteStatusUseCase
import com.sharapov.core_domain.usecases.GetAnimeByIdUseCase
import com.sharapov.feature_details_screen.mapper.toUiModel
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
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted("id") private val id: Int,
    private val getAnimeByIdUseCase: GetAnimeByIdUseCase,
    private val changeAnimeFavoriteStatusUseCase: ChangeAnimeFavoriteStatusUseCase
): ViewModel() {

    private val _state = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Initial)
    val state = _state.asStateFlow()

    init {
        getAnimeByIdUseCase(id)
            .onStart {
                _state.value = DetailsScreenState.Loading
            }
            .onEach { anime ->
                _state.value = DetailsScreenState.Content(anime.toUiModel())
            }
            .catch { e -> _state.value = DetailsScreenState.Error(e.cause?.message ?: "Unknown message") }
            .launchIn(viewModelScope)
    }

    fun processCommand(command: DetailsScreenCommand) {
        when(command) {
            is DetailsScreenCommand.ChangeFavoriteStatus -> {
                viewModelScope.launch {
                    changeAnimeFavoriteStatusUseCase(id)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("id") id: Int): DetailsViewModel
    }
}