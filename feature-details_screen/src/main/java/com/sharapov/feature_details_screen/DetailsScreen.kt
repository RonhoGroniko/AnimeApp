package com.sharapov.feature_details_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sharapov.core_ui.theme.core.BasePane
import com.sharapov.core_ui.theme.core.LceState

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    animeId: Long,
    viewModel: DetailsViewModel = hiltViewModel { factory: DetailsViewModel.Factory ->
        factory.create(animeId)
    },
) {
    val state = viewModel.state.collectAsState()
    BasePane(
        modifier = modifier,
        lceState = state.value,
    ) { innerPadding, contentState ->
        DetailsScreenContent(
            contentState = contentState,
            innerPadding = innerPadding
        )
    }
}

@Composable
fun DetailsScreenContent(
    contentState: LceState.Content<DetailsScreenContent>,
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
    ) {
        Text(contentState.data.anime.toString())
    }
}