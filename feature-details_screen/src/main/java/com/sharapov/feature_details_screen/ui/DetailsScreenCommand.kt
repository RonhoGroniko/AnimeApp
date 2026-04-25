package com.sharapov.feature_details_screen.ui

import com.sharapov.feature_details_screen.ui.model.AnimeDetailsUiModel

sealed interface DetailsScreenCommand {

    data class ChangeFavoriteStatus(val anime: AnimeDetailsUiModel): DetailsScreenCommand
}