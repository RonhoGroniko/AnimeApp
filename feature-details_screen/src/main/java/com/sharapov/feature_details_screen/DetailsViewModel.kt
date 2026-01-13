package com.sharapov.feature_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharapov.core_domain.Result
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.toErrorType
import com.sharapov.core_ui.theme.core.toLceState
import com.sharapov.core_ui.theme.core.toUiMessage
import com.sharapov.domain_anime.usecases.details.GetAnimeByIdUseCase
import com.sharapov.domain_anime.usecases.favorites.ChangeFavoriteStatusUseCase
import com.sharapov.domain_anime.usecases.favorites.GetFavoriteStatusUseCase
import com.sharapov.feature_details_screen.mapper.toEntityListItem
import com.sharapov.feature_details_screen.mapper.toUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted("animeId") private val animeId: Long,
    private val getAnimeByIdUseCase: GetAnimeByIdUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase,
    private val getFavoriteStatusUseCase: GetFavoriteStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsScreenState>(LceState.Initial)
    val state = _state.asStateFlow()

    init {
        loadAnimeById(animeId)
    }

    private fun loadAnimeById(animeId: Long) {
        combine(
            getAnimeByIdUseCase(animeId),
            getFavoriteStatusUseCase(animeId)
        ) { animeResult, favoriteResult ->
            when (favoriteResult) {
                is Result.Error -> {
                    val type = favoriteResult.exception.toErrorType()
                    LceState.Error(
                        type = type,
                        message = type.toUiMessage(favoriteResult.message)
                    )
                }

                Result.Loading -> { LceState.Loading } // should never happen :)
                is Result.Success -> {
                    val isFavorite = favoriteResult.data
                    animeResult.toLceState { anime ->
                        DetailsScreenContent(anime.toUi(isFavorite))
                    }
                }
            }
        }.onEach {
            _state.value = it
        }.launchIn(viewModelScope)
    }

    fun processCommand(command: DetailsScreenCommand) {
        when(command) {

            is DetailsScreenCommand.ChangeFavoriteStatus -> {
                viewModelScope.launch {
                    val result = changeFavoriteStatusUseCase(
                        anime = command.anime.toEntityListItem(),
                        makeFavorite = !command.anime.isFavorite
                    )
                    if (result is Result.Error) {
                        val type = result.exception.toErrorType()
                        _state.value = LceState.Error(
                            type = type,
                            message = type.toUiMessage(result.message)
                        )
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("animeId") animeId: Long): DetailsViewModel
    }
}