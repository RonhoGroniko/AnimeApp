package com.sharapov.feature_main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.composable.AnimeCardPlaceholder
import com.sharapov.core_ui.theme.core.BasePane
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.core.SectionState
import com.sharapov.domain_anime.entity.AnimeListItem

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
        topBar = { TopMainScreenBar(
            onSettingsClick = onSettingsClick
        ) }
    ) { innerPadding, contentState ->
        MainScreenContent(
            contentState = contentState,
            innerPadding = innerPadding,
            onCardClick = onCardClick
        )
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
        Subtitle(text = subtitle)
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

@Composable
private fun Subtitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier.padding(start = 24.dp),
        text = text,
        fontFamily = CustomFonts.Poppins,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 16.sp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopMainScreenBar(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Tsundoku",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 24.sp,
                fontFamily = CustomFonts.Poppins,
                fontWeight = FontWeight.ExtraBold
            )
        },
        actions = {
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(CircleShape)
                    .clickable {
                        onSettingsClick()
                    },
                imageVector = Icons.Default.Settings,
                contentDescription = "Setting button",
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

