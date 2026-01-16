package com.sharapov.feature_details_screen

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.sharapov.core_ui.R
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.core.BasePane
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.domain_anime.entity.details.Character
import com.sharapov.domain_anime.entity.details.Screenshot
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.feature_details_screen.mapper.episodesToUi

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
                    }
                )
            }

            is LceState.Error -> {}
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
    onBackClick: () -> Unit,
    onCardClick: (Long) -> Unit,
    onGenreClick: (String) -> Unit,
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
        }
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
                    imageUrl = contentState.data.anime.imageUrl,
                    title = contentState.data.anime.name,
                    studios = contentState.data.anime.studios,
                    releaseDate = contentState.data.anime.releasedOnDate,
                    mean = contentState.data.anime.score,
                    isFavorite = contentState.data.anime.isFavorite,
                    onBackClick = onBackClick,
                    onChangeFavoriteStatus = onChangeFavoriteStatus
                )
            }
            item {
                GenreChips(
                    genres = contentState.data.anime.genres,
                    onGenreClick = onGenreClick
                )
            }
            item {
                InfoRow(
                    status = contentState.data.anime.status,
                    rating = contentState.data.anime.rating,
                    numEpisodes = episodesToUi(
                        contentState.data.anime.episodesAired,
                        contentState.data.anime.episodes
                    ),
                    episodeDuration = contentState.data.anime.duration.toString(),
                    animeKind = contentState.data.anime.kind
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
                        text = "Description"
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
                        text = "Screenshots"
                    )
                }
                item { ScreenshotRow(pictureUrls = contentState.data.anime.screenshotsUrls) }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
            if (contentState.data.anime.characters.isNotEmpty()) {
                item {
                    Subtitle(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            bottom = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        text = "Characters"
                    )
                }
                item { CharacterRow(characters =  contentState.data.anime.characters) }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
            if (contentState.data.anime.relatedAnime.isNotEmpty()) {
                item {
                    Subtitle(
                        modifier = Modifier.padding(
                            top = 8.dp,
                            bottom = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        text = "Related Anime"
                    )
                }
                item {
                    AnimeCardsRowWithTitle(
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
                        modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                        text = "Chronology"
                    )
                }
                item {
                    AnimeCardsRowWithTitle(
                        animeList = contentState.data.anime.chronology.map { it.animeListItem },
                        titleList = contentState.data.anime.chronology.map { it.kind.value },
                        onCardClick = onCardClick
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }
        }

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
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                    if (studios.isNotEmpty()) {
                        Text(
                            text = "Studios: ${studios.joinToString()}",
                            fontFamily = CustomFonts.Poppins,
                            color = MaterialTheme.colorScheme.primary,
                            fontStyle = FontStyle.Italic,
                            fontSize = 14.sp
                        )
                    }

                    if (releaseDate.isNotBlank()) {
                        Text(
                            text = "Release date: $releaseDate",
                            fontFamily = CustomFonts.Poppins,
                            color = MaterialTheme.colorScheme.primary,
                            fontStyle = FontStyle.Italic,
                            fontSize = 14.sp
                        )
                    }
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
            textAlign = TextAlign.End,
            fontStyle = FontStyle.Italic
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
    onGenreClick: (String) -> Unit
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
                        onGenreClick(genre)
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
                        color = MaterialTheme.colorScheme.onSecondaryContainer
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
    animeKind: String
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
            content = animeKind
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
                            val height = fadeHeight.toPx()
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, fadeBg),
                                    startY = size.height - height,
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
                            indication = null,
                            interactionSource = interaction,
                        ) { expanded = !expanded }
                        .padding(bottom = 2.dp)
                )
            }
        }
    }
}

@Composable
fun ScreenshotRow(
    modifier: Modifier = Modifier,
    pictureUrls: List<Screenshot>
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(pictureUrls, key = { it.id }) { screenshot ->
            SubcomposeAsyncImage(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .aspectRatio(3 / 2f, true)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp)),
                model = screenshot.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "Anime picture",
            ) {
                val painterState = painter.state.collectAsState()
                when (painterState.value) {
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFE6E6E6)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterRow(
    modifier: Modifier = Modifier,
    characters: List<Character>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(characters, key = { it.id }) { character ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.widthIn(max = 120.dp).height(32.dp),
                    text = character.roles[0],
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = CustomFonts.Poppins,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .height(160.dp)
                        .width(120.dp)
                        .clip(RoundedCornerShape(40.dp)),
                    model = character.imageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Character picture"
                ) {
                    val painterState = painter.state.collectAsState()
                    when (painterState.value) {
                        is AsyncImagePainter.State.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFFE6E6E6)),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        else -> {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
                Text(
                    modifier = Modifier.widthIn(max = 120.dp),
                    text = character.name,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = CustomFonts.Poppins,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
private fun AnimeCardsRowWithTitle(
    modifier: Modifier = Modifier,
    animeList: List<AnimeListItem>,
    titleList: List<String>,
    onCardClick: (Long) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        animeList.forEachIndexed { index, anime ->
            item(key = anime.id) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.widthIn(160.dp),
                        text = titleList[index],
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = CustomFonts.Poppins,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    AnimeCard(
                        modifier = Modifier.padding(8.dp),
                        anime = anime,
                        onCardClick = { onCardClick(anime.id) }
                    )
                }
            }
        }
    }
}

