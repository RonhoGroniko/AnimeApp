package com.sharapov.feature_details_screen

import com.sharapov.feature_details_screen.model.AnimeDetailsUiModel

sealed interface DetailsScreenCommand {

    data class ChangeFavoriteStatus(val anime: AnimeDetailsUiModel): DetailsScreenCommand
}