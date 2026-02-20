package com.sharapov.feature_details_screen.ui.composables

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.sharapov.core_ui.theme.composable.ShimmerBox
import com.sharapov.feature_details_screen.domain.entity.Screenshot


@Composable
internal fun ScreenshotRow(
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
                    is AsyncImagePainter.State.Success -> {
                        SubcomposeAsyncImageContent()
                    }

                    else -> {
                        ShimmerBox()
                    }
                }
            }
        }
    }
}