package com.sharapov.feature_details_screen.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.AnimeCard

@Composable
internal fun AnimeCardWithTitleRow(
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
