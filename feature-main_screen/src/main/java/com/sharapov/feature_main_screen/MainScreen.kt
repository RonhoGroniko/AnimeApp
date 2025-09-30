@file:OptIn(ExperimentalMaterial3Api::class)

package com.sharapov.feature_main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.sharapov.core_domain.entity.Anime
import com.sharapov.core_ui.theme.CustomFonts

@Composable
fun MainScreen(
    viewModel: ScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    when (val currentState = state.value) {
        is MainScreenState.Content -> {
            MainScreenContent(
                animeList = currentState.animeList,
                onLoadDataClick = {
                    viewModel.processCommand(MainScreenCommand.RefreshData)
                }
            )
        }

        MainScreenState.Initial -> {

        }

        MainScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    animeList: List<Anime>,
    onLoadDataClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            TopMainScreenBar(
                onSettingsClick = { onLoadDataClick() }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            item {
                if (animeList.isNotEmpty()) {
                    Subtitle(
                        modifier = Modifier.padding(start = 24.dp),
                        text = "New releases"
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    animeList.forEach { anime ->
                        item(key = anime.id) {
                            AnimeCard(
                                imageUrl = anime.imageUrl,
                                title = anime.title
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopMainScreenBar(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier.padding(horizontal = 16.dp),
        title = {
            Text(
                text = "Tsundoku",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        actions = {
            Icon(
                modifier = Modifier.clickable {
                    onSettingsClick()
                },
                imageVector = Icons.Default.Settings,
                contentDescription = "Setting button"
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun AnimeCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .height(240.dp)
            .width(120.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            model = imageUrl,
            contentDescription = "Anime image",
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(bottom = 8.dp, end = 8.dp, start = 8.dp),
            text = title,
            overflow = TextOverflow.Ellipsis,
            fontFamily = CustomFonts.Poppins
        )
    }
}

@Composable
private fun Subtitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = CustomFonts.Poppins,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 16.sp
    )
}
