@file:OptIn(ExperimentalMaterial3Api::class)

package com.sharapov.feature_details_screen.ui

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sharapov.core_ui.R
import com.sharapov.core_ui.theme.composable.ErrorWithImage
import com.sharapov.core_ui.theme.composable.Subtitle
import com.sharapov.core_ui.theme.core.BasePane
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.feature_details_screen.ui.composables.AnimeCardWithTitleRow
import com.sharapov.feature_details_screen.ui.composables.CharacterRow
import com.sharapov.feature_details_screen.ui.composables.ExpandableDescription
import com.sharapov.feature_details_screen.ui.composables.GenreChipRow
import com.sharapov.feature_details_screen.ui.composables.HeaderCard
import com.sharapov.feature_details_screen.ui.composables.InfoRow
import com.sharapov.feature_details_screen.ui.composables.OverlayTopAppBar
import com.sharapov.feature_details_screen.ui.composables.ScreenshotRow
import com.sharapov.feature_details_screen.ui.composables.StatisticBottomSheet
import com.sharapov.feature_details_screen.ui.composables.VideoRow
import com.sharapov.feature_details_screen.data.mapper.episodesToUi
import kotlinx.coroutines.CoroutineScope
import kotlin.collections.isNotEmpty
import kotlin.collections.map

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    animeId: Long,
    viewModel: DetailsViewModel = hiltViewModel { factory: DetailsViewModel.Factory ->
        factory.create(animeId)
    },
    onBackClick: () -> Unit,
    onCardClick: (Long) -> Unit,
    onGenreClick: (String) -> Unit,
) {
    val state = viewModel.state.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    BasePane(
        modifier = modifier,
        lceState = state.value,
    ) { innerPadding, currentState ->
        when (currentState) {
            is LceState.Content<DetailsScreenContent> -> {
                DetailsScreenContent(
                    contentState = currentState,
                    innerPadding = innerPadding,
                    onBackClick = onBackClick,
                    onCardClick = onCardClick,
                    onGenreClick = onGenreClick,
                    onChangeFavoriteStatus = {
                        viewModel.processCommand(
                            DetailsScreenCommand.ChangeFavoriteStatus(
                                anime = currentState.data.anime
                            )
                        )
                    },
                    onStatisticClick = {
                        showBottomSheet = true
                    },
                    sheetState = sheetState,
                    showBottomSheet = showBottomSheet,
                    scope = scope,
                    onDismiss = {
                        showBottomSheet = false
                    }
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
fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    contentState: LceState.Content<DetailsScreenContent>,
    sheetState: SheetState,
    scope: CoroutineScope,
    showBottomSheet: Boolean,
    onBackClick: () -> Unit,
    onCardClick: (Long) -> Unit,
    onGenreClick: (String) -> Unit,
    onChangeFavoriteStatus: () -> Unit,
    onStatisticClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val thresholdPx = with(density) { 96.dp.toPx() }

    val showBar by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 ||
                    listState.firstVisibleItemScrollOffset > thresholdPx
        }
    }
    val alpha by animateFloatAsState(
        if (showBar) {
            1f
        } else {
            0f
        }
    )

    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                HeaderCard(
                    imageUrl = contentState.data.anime.imageUrl,
                    title = contentState.data.anime.name,
                    studios = contentState.data.anime.studios,
                    releaseDate = contentState.data.anime.releasedOnDate,
                    mean = contentState.data.anime.score,
                    isFavorite = contentState.data.anime.isFavorite,
                    onBackClick = onBackClick,
                    onChangeFavoriteStatus = onChangeFavoriteStatus,
                    onStatisticClick = onStatisticClick
                )
            }
            item {
                GenreChipRow(
                    genres = contentState.data.anime.genres,
                    onGenreClick = onGenreClick
                )
            }
            item {
                InfoRow(
                    labelList = listOf(
                        "Status",
                        "Type",
                        "Rating",
                        "Episodes",
                        "Duration"
                    ),
                    contentList = listOf(
                        contentState.data.anime.status,
                        contentState.data.anime.kind,
                        contentState.data.anime.rating,
                        episodesToUi(
                            contentState.data.anime.episodesAired,
                            contentState.data.anime.episodes
                        ),
                        contentState.data.anime.duration.toString()
                    )
                )
            }
            if (contentState.data.anime.description.isNotBlank()) {
                item {
                    Subtitle(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            bottom = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        text = "Description",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                item {
                    ExpandableDescription(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = contentState.data.anime.description
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
            if (contentState.data.anime.screenshotsUrls.isNotEmpty()) {
                item {
                    Subtitle(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            bottom = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        text = "Screenshots",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                item { ScreenshotRow(pictureUrls = contentState.data.anime.screenshotsUrls) }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
            if (contentState.data.anime.videos.isNotEmpty()) {
                item {
                    Subtitle(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "Videos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                item {
                    VideoRow(
                        videos = contentState.data.anime.videos,
                        onVideoClick = { uri ->
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
            if (contentState.data.anime.characters.isNotEmpty()) {
                item {
                    Subtitle(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        text = "Characters",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                item { CharacterRow(characters = contentState.data.anime.characters) }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
            if (contentState.data.anime.relatedAnime.isNotEmpty()) {
                item {
                    Subtitle(
                        modifier = Modifier.padding(
                            top = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        text = "Related Anime",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                item {
                    AnimeCardWithTitleRow(
                        animeList = contentState.data.anime.relatedAnime.map { it.animeListItem },
                        titleList = contentState.data.anime.relatedAnime.map { it.relationKind.value },
                        onCardClick = onCardClick
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
            if (contentState.data.anime.chronology.isNotEmpty()) {
                item {
                    Subtitle(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        text = "Chronology",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                item {
                    AnimeCardWithTitleRow(
                        animeList = contentState.data.anime.chronology.map { it.animeListItem },
                        titleList = contentState.data.anime.chronology.map { it.kind.value },
                        onCardClick = onCardClick
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
        }

        StatisticBottomSheet(
            innerPadding = innerPadding,
            onDismiss = onDismiss,
            showBottomSheet = showBottomSheet,
            sheetState = sheetState,
            scope = scope,
            statusStats = contentState.data.anime.statusStats,
            scoreStats = contentState.data.anime.scoreStats
        )

        OverlayTopAppBar(
            title = contentState.data.anime.name,
            alpha = alpha,
            onBackClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        )
    }
}
