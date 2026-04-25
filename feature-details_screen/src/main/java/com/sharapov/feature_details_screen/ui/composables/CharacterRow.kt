package com.sharapov.feature_details_screen.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.ShimmerBox
import com.sharapov.feature_details_screen.domain.entity.Character
import kotlin.collections.get

@Composable
internal fun CharacterRow(
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
                    modifier = Modifier
                        .widthIn(max = 120.dp)
                        .height(32.dp),
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
                        is AsyncImagePainter.State.Success -> {
                            SubcomposeAsyncImageContent()
                        }

                        else -> {
                            ShimmerBox()
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