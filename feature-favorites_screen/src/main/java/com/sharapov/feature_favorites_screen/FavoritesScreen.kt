package com.sharapov.feature_favorites_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sharapov.core_ui.R
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.composable.ErrorWithImage
import com.sharapov.core_ui.theme.core.BasePane
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.feature_favorites_screen.composables.TopFavoritesAppBar

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onCardClick: (Long) -> Unit
) {

    val state = viewModel.state.collectAsState()

    BasePane(
        topBar = { TopFavoritesAppBar() },
        lceState = state.value
    ) { innerPadding, currentState ->
        when (currentState) {
            is LceState.Content<FavoritesScreenContent> -> {
                FavoriteScreenContent(
                    innerPadding = innerPadding,
                    animeList = currentState.data.animeList,
                    onCardClick = onCardClick
                )
            }

            is LceState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorWithImage("Бака сервер потерял записи, хех", R.drawable.confused_anime_girl)
                }
            }

            LceState.Initial -> {}

            LceState.Loading -> {}
        }
    }
}

@Composable
private fun FavoriteScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    animeList: List<AnimeListItem>,
    onCardClick: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(animeList, key = { it.id }) { anime ->
            AnimeCard(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth()
                    .aspectRatio(0.72f),
                anime = anime,
                onCardClick = onCardClick
            )
        }
    }
}
