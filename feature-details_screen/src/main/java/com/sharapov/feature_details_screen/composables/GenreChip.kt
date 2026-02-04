package com.sharapov.feature_details_screen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharapov.core_ui.theme.CustomFonts

@Composable
internal fun GenreChipRow(
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
                GenreChip(
                    genre = genre,
                    onGenreClick = {
                        onGenreClick(genre)
                    }
                )
            }
        }
    }
}

@Composable
private fun GenreChip(
    modifier: Modifier = Modifier,
    genre: String,
    onGenreClick: () -> Unit
) {
    ElevatedSuggestionChip(
        modifier = modifier,
        onClick = {
            onGenreClick()
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