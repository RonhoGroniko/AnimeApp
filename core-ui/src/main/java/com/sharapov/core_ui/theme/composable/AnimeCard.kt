package com.sharapov.core_ui.theme.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_ui.theme.CustomFonts

@Composable
fun AnimeCard(
    modifier: Modifier = Modifier,
    anime: Anime,
    onCardClick: (Int) -> Unit
) {
    var backEnabled by remember { mutableStateOf(true) }
    Card(
        modifier = modifier
            .padding(8.dp)
            .height(240.dp)
            .width(120.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.background
        ),
        onClick = {
            if (backEnabled) {
                backEnabled = false
                onCardClick(anime.id)
            }
        },
        enabled = backEnabled,
        shape = RoundedCornerShape(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                .heightIn(max = 200.dp),
            model = anime.imageUrl,
            contentDescription = "Anime image",
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = anime.title,
            overflow = TextOverflow.Ellipsis,
            fontFamily = CustomFonts.Poppins,
            textAlign = TextAlign.Center
        )
    }
}