package com.sharapov.feature_details_screen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.ShimmerBox
import com.sharapov.core_ui.theme.composable.Subtitle
import com.sharapov.core_ui.theme.icons.ArrowBack
import com.sharapov.core_ui.theme.icons.BarChart
import com.sharapov.core_ui.theme.icons.Bookmark
import com.sharapov.core_ui.theme.icons.CustomIcons

@Composable
internal fun HeaderCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    studios: List<String>,
    releaseDate: String,
    mean: Double,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onChangeFavoriteStatus: () -> Unit,
    onStatisticClick: () -> Unit
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
            Box {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = "Image for selected card",
                    modifier = Modifier
                        .heightIn(max = 480.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillWidth
                ) {
                    val painter = painter.state.collectAsState()
                    when (painter.value) {
                        is AsyncImagePainter.State.Success -> {
                            SubcomposeAsyncImageContent()
                        }

                        else -> {
                            ShimmerBox()
                        }
                    }
                }

                BackButton(
                    modifier = Modifier.align(Alignment.TopStart),
                    backEnabled = backEnabled,
                    onBackClick = {
                        backEnabled = false
                        onBackClick()
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Subtitle(
                    modifier = Modifier.weight(1f),
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                )

                IconButton(
                    onClick = { onStatisticClick() },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = CustomIcons.Filled.BarChart,
                        contentDescription = "Open statistics",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                IconButton(
                    onClick = { onChangeFavoriteStatus() },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = if (isFavorite) CustomIcons.Filled.Bookmark else CustomIcons.Outlined.Bookmark,
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
private fun BackButton(
    modifier: Modifier = Modifier,
    backEnabled: Boolean,
    onBackClick: () -> Unit
) {
    IconButton(
        onClick = {
            if (backEnabled) {
                onBackClick()
            }
        },
        enabled = backEnabled,
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            imageVector = CustomIcons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}