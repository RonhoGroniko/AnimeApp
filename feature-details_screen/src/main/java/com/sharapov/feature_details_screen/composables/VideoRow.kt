package com.sharapov.feature_details_screen.composables

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.ShimmerBox
import com.sharapov.domain_anime.entity.details.Video


@Composable
internal fun VideoRow(
    modifier: Modifier = Modifier,
    videos: List<Video>,
    onVideoClick: (Uri) -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(videos, key = { it.id }) { video ->
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = video.kind.value,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = CustomFonts.Poppins,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .aspectRatio(3 / 2f, true)
                            .fillMaxHeight()
                            .clickable {
                                onVideoClick(video.url.toUri())
                            },
                        model = video.imageUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Video ${video.kind.value}",
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
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.scrim
                                    )
                                )
                            )
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = video.name,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontFamily = CustomFonts.Poppins,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 3
                            )
                        }
                    }
                }
            }
        }
    }
}

