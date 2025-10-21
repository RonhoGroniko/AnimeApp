package com.sharapov.feature_details_screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_domain.usecases.AnimeFilter
import com.sharapov.core_ui.R
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.composable.BasePane
import com.sharapov.feature_details_screen.model.AnimeWithDetailsUiModel
import com.sharapov.feature_details_screen.model.RelatedAnimeUiModel

@Composable
fun DetailsScreen(
    id: Int,
    viewModel: DetailsViewModel = hiltViewModel { factory: DetailsViewModel.Factory ->
        factory.create(id)
    },
    onBackClick: () -> Unit,
    onCardClick: (Int) -> Unit,
    onGenreClick: (AnimeFilter) -> Unit
) {
    BasePane(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) { innerPadding ->
        val state = viewModel.state.collectAsState()
        when (val currentState = state.value) {
            is DetailsScreenState.Content -> {
                DetailsScreenContent(
                    innerPadding = innerPadding,
                    anime = currentState.anime,
                    onBackClick = onBackClick,
                    onCardClick = onCardClick,
                    onGenreClick = onGenreClick,
                    onChangeFavoriteStatus = { viewModel.processCommand(DetailsScreenCommand.ChangeFavoriteStatus) }
                )
            }

            is DetailsScreenState.Error -> {
                Log.d("ERROR", currentState.message)
            }

            DetailsScreenState.Initial -> {}
            DetailsScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    anime: AnimeWithDetailsUiModel,
    onBackClick: () -> Unit,
    onCardClick: (Int) -> Unit,
    onGenreClick: (AnimeFilter) -> Unit,
    onChangeFavoriteStatus: () -> Unit
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
        }, label = "topbar_alpha"
    )

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
                    imageUrl = anime.mainPicture,
                    title = anime.title,
                    studios = anime.studios,
                    releaseDate = anime.startDate,
                    mean = anime.mean,
                    isFavorite = anime.isFavorite,
                    onBackClick = onBackClick,
                    onChangeFavoriteStatus = onChangeFavoriteStatus
                )
            }
            item {
                GenreChips(
                    genres = anime.genres,
                    onGenreClick = onGenreClick
                )
            }
            item {
                InfoRow(
                    status = anime.status,
                    rating = anime.rating,
                    numEpisodes = anime.numEpisodes,
                    episodeDuration = anime.averageEpisodeDuration,
                    mediaType = anime.mediaType
                )
            }
            if (anime.synopsis.isNotBlank()) {
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Description"
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item {
                    ExpandableDescription(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = anime.synopsis
                    )
                }
            }
            if (anime.pictures.isNotEmpty()) {
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Pictures"
                    )
                }
                item { Spacer(modifier = Modifier.height(4.dp)) }
                item { PictureCardRow(pictureUrls = anime.pictures) }
            }
            if (anime.background.isNotBlank()) {
                item { Spacer(modifier = Modifier.height(4.dp)) }
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Background"
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item {
                    ExpandableDescription(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = anime.background
                    )
                }
            }
            if (anime.relatedAnime.isNotEmpty()) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Related Anime"
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item {
                    RelatedAnimeCardsRow(
                        relatedAnimeList = anime.relatedAnime,
                        onCardClick = onCardClick
                    )
                }
            }
            if (anime.recommendations.isNotEmpty()) {
                item {
                    Subtitle(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Recommendations"
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item {
                    AnimeCardsRow(
                        animeList = anime.recommendations,
                        onCardClick = onCardClick
                    )
                }
            }
        }

        OverlayTopAppBar(
            title = anime.title,
            alpha = alpha,
            onBackClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        )
    }
}


@Composable
private fun OverlayTopAppBar(
    title: String,
    alpha: Float,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .background(MaterialTheme.colorScheme.background.copy(alpha = alpha)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            enabled = alpha > 0.001f
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
            )
        }
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.secondary.copy(alpha = alpha),
            fontFamily = CustomFonts.Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun HeaderCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    studios: List<String>,
    releaseDate: String,
    mean: Double,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onChangeFavoriteStatus: () -> Unit
) {
    var backEnabled by remember { mutableStateOf(true) }

    Card(
        modifier = modifier
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Box() {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Image for selected card",
                    modifier = Modifier
                        .heightIn(max = 480.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillWidth
                )
                IconButton(
                    onClick = {
                        if (backEnabled) {
                            backEnabled = false
                            onBackClick()
                        }
                    },
                    enabled = backEnabled,
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    fontFamily = CustomFonts.Poppins,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 24.sp
                )
                IconButton(
                    onClick = { onChangeFavoriteStatus() },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Change isFavorite status",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(0.6f)) {
                    Text(
                        text = "Studios: ${studios.joinToString()}",
                        fontFamily = CustomFonts.Poppins,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        fontStyle = FontStyle.Italic,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Release date: $releaseDate",
                        fontFamily = CustomFonts.Poppins,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        fontStyle = FontStyle.Italic,
                        fontSize = 14.sp
                    )
                }
                StarsRating(
                    modifier = Modifier.weight(0.4f),
                    mean = mean
                )
            }
        }
    }
}

@Composable
private fun StarsRating(
    modifier: Modifier = Modifier,
    mean: Double
) {
    val stars = calculateStars(mean)
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "Rating: $mean",
            fontFamily = CustomFonts.Poppins,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            textAlign = TextAlign.End
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(stars.fullStars) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            repeat(stars.halfStars) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_star_half),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            repeat(stars.emptyStars) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                )
            }
        }
    }

}

data class StarState(
    val fullStars: Int,
    val halfStars: Int,
    val emptyStars: Int
)

private fun calculateStars(mean: Double): StarState {
    val starValue = (mean / 2.0).coerceIn(0.0, 5.0)

    val fullStars = starValue.toInt()
    val hasHalfStar = (starValue - fullStars) >= 0.5

    val halfStars = if (hasHalfStar) 1 else 0
    val emptyStars = 5 - fullStars - halfStars

    return StarState(
        fullStars = fullStars,
        halfStars = halfStars,
        emptyStars = emptyStars
    )
}

@Composable
fun GenreChips(
    modifier: Modifier = Modifier,
    genres: List<String>,
    onGenreClick: (AnimeFilter) -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genres.forEach { genre ->
            item {
                ElevatedSuggestionChip(
                    onClick = {
                        onGenreClick(AnimeFilter.ByGenre(genre))
                    },
                    label = {
                        Text(
                            text = genre,
                            fontFamily = CustomFonts.Poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp
                        )
                    },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = SuggestionChipDefaults.elevatedSuggestionChipElevation(
                        elevation = 4.dp,
                        pressedElevation = 8.dp,
                    ),
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                    )
                )
            }
        }

    }
}

@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    status: String,
    rating: String,
    numEpisodes: String,
    episodeDuration: String,
    mediaType: String
) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfoColumn(
            modifier = Modifier.weight(1f),
            label = "Status",
            content = status
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.background
        )
        InfoColumn(
            modifier = Modifier.weight(1f),
            label = "Type",
            content = mediaType
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.background
        )
        InfoColumn(
            modifier = Modifier.weight(1f),
            label = "Rating",
            content = rating
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.background
        )
        InfoColumn(
            modifier = Modifier.weight(1f),
            label = "Episodes",
            content = numEpisodes
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.background
        )
        InfoColumn(
            modifier = Modifier.weight(1f),
            label = "Duration",
            content = episodeDuration
        )
    }
}

@Composable
private fun InfoColumn(
    modifier: Modifier = Modifier,
    label: String,
    content: String
) {
    Column(
        modifier = modifier
            .heightIn(min = 56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontFamily = CustomFonts.Poppins,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = content,
                textAlign = TextAlign.Center,
                fontFamily = CustomFonts.Poppins,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp
            )
        }
    }
}


@Composable
fun Subtitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = CustomFonts.Poppins
    )
}

@Composable
fun ExpandableDescription(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLines: Int = 4
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var hasOverflow by remember { mutableStateOf(false) }

    val fadeBg = MaterialTheme.colorScheme.surfaceVariant
    val fadeHeight = 48.dp
    val rotation by animateFloatAsState(if (expanded) 180f else 0f, label = "arrow_rotation")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .animateContentSize()
    ) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .graphicsLayer { alpha = 0.99f }
                    .drawWithContent {
                        drawContent()
                        if (!expanded && hasOverflow) {
                            val h = fadeHeight.toPx()
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, fadeBg),
                                    startY = size.height - h,
                                    endY = size.height
                                )
                            )
                        }
                    },
                maxLines = if (expanded) Int.MAX_VALUE else collapsedMaxLines,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { hasOverflow = it.hasVisualOverflow },
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                fontFamily = CustomFonts.Poppins
            )

            if (hasOverflow || expanded) {
                val interaction = remember { MutableInteractionSource() }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .graphicsLayer { rotationZ = rotation }
                        .size(24.dp)
                        .clickable(
                            interactionSource = interaction,
                        ) { expanded = !expanded }
                        .padding(bottom = 2.dp)
                )
            }
        }
    }
}

@Composable
fun PictureCardRow(
    modifier: Modifier = Modifier,
    pictureUrls: List<String>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            pictureUrls.forEach { imageUrl ->
                AsyncImage(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .heightIn(max = 200.dp),
                    model = imageUrl,
                    contentDescription = "Anime picture",
                )
            }

        }
    }
}


@Composable
private fun RelatedAnimeCardsRow(
    modifier: Modifier = Modifier,
    relatedAnimeList: List<RelatedAnimeUiModel>,
    onCardClick: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        relatedAnimeList.forEach { relatedAnime ->
            item(key = relatedAnime.anime.id) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = relatedAnime.relation,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = CustomFonts.Poppins
                    )
                    AnimeCard(
                        modifier = Modifier.padding(8.dp),
                        anime = relatedAnime.anime,
                        onCardClick = { onCardClick(relatedAnime.anime.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun AnimeCardsRow(
    modifier: Modifier = Modifier,
    animeList: List<Anime>,
    onCardClick: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        animeList.forEach { anime ->
            item(key = anime.id) {
                AnimeCard(
                    modifier = Modifier.padding(8.dp),
                    anime = anime,
                    onCardClick = { onCardClick(anime.id) }
                )
            }
        }
    }
}