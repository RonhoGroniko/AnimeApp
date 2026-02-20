package com.sharapov.feature_main_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.core_ui.R
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.composable.AnimeCardPlaceholder
import com.sharapov.core_ui.theme.composable.ErrorWithImage
import com.sharapov.core_ui.theme.core.BasePane
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.SectionState
import com.sharapov.feature_main_screen.ui.composables.TopMainScreenBar
import com.sharapov.core_ui.theme.composable.Subtitle

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    onCardClick: (Long) -> Unit,
    onSettingsClick: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    BasePane(
        modifier = modifier,
        lceState = state.value,
        topBar = {
            TopMainScreenBar(
                onSettingsClick = onSettingsClick
            )
        }
    ) { innerPadding, currentState ->
        when (currentState) {
            is LceState.Content<MainScreenContent> -> {
                MainScreenContent(
                    contentState = currentState,
                    innerPadding = innerPadding,
                    onCardClick = onCardClick
                )
            }

            is LceState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorWithImage(
                        "Бака сервер потерял записи, хех",
                        R.drawable.confused_anime_girl
                    )
                }
            }

            LceState.Initial -> {}
            LceState.Loading -> {}
        }
    }
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    contentState: LceState.Content<MainScreenContent>,
    innerPadding: PaddingValues,
    onCardClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = innerPadding
    ) {
        item {
            AnimeCardsRow(
                animeListState = contentState.data.upcomingAnimeList,
                onCardClick = onCardClick,
                subtitle = "Upcoming"
            )
        }
        item {
            AnimeCardsRow(
                animeListState = contentState.data.airingAnimeList,
                onCardClick = onCardClick,
                subtitle = "Top Airing"
            )
        }
        item {
            AnimeCardsRow(
                animeListState = contentState.data.releasedAnimeList,
                onCardClick = onCardClick,
                subtitle = "Released"
            )
        }
    }
}


@Composable
fun AnimeCardsRow(
    modifier: Modifier = Modifier,
    animeListState: SectionState<List<AnimeListItem>>,
    subtitle: String,
    onCardClick: (Long) -> Unit
) {
    Column {
        Subtitle(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = subtitle,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (animeListState) {
                is SectionState.Content<List<AnimeListItem>> -> {
                    items(items = animeListState.data, key = { it.id }) { anime ->
                        AnimeCard(
                            modifier = Modifier.padding(8.dp),
                            anime = anime,
                            onCardClick = { onCardClick(anime.id) }
                        )
                    }
                }

                is SectionState.Error -> {
                    items(5) {
                        AnimeCardPlaceholder()
                    }
                }

                SectionState.Initial -> {}

                SectionState.Loading -> {
                    items(5) {
                        AnimeCardPlaceholder()
                    }
                }
            }
        }
    }
}

