package com.sharapov.feature_details_screen

import com.sharapov.feature_details_screen.model.AnimeWithDetailsUiModel

sealed interface DetailsScreenState {

    data object Initial: DetailsScreenState

    data object Loading: DetailsScreenState

    data class Error(val message: String): DetailsScreenState

    data class Content(val anime: AnimeWithDetailsUiModel): DetailsScreenState
}