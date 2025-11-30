package com.sharapov.feature_details_screen

import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.feature_details_screen.model.AnimeDetailsUiModel

typealias DetailsScreenState = LceState<DetailsScreenContent>

data class DetailsScreenContent(
    val anime: AnimeDetailsUiModel
)