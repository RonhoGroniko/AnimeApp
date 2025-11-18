@file:OptIn(ExperimentalMaterial3Api::class)

package com.sharapov.feature_main_screen.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sharapov.domain_anime.entity.Anime
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.composable.BasePane
import com.sharapov.feature_main_screen.utils.isInternetAvailable

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    onCardClick: (Int) -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current
    var isOnline by remember { mutableStateOf(isInternetAvailable(context)) }

    DisposableEffect(Unit) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isOnline = true
            }

            override fun onLost(network: Network) {
                isOnline = false
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)
        onDispose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
    BasePane(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            TopMainScreenBar(
                onSettingsClick = onSettingsClick,
                onRefreshDataClick = { viewModel.processCommand(MainScreenCommand.RefreshData) },
                isOnline = isOnline
            )
        }
    ) { innerPadding ->

        val state = viewModel.state.collectAsState()

        when (val currentState = state.value) {
            is MainScreenState.Content -> {
                MainScreenContent(
                    innerPadding = innerPadding,
                    onCardClick = onCardClick,
                    state = currentState
                )
            }

            MainScreenState.Initial -> {}

            MainScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            is MainScreenState.Error -> {
                Log.d("TEST", currentState.message)
                NetworkProblemField(
                    modifier = Modifier.fillMaxSize(),
                    onRefreshDataClick = { viewModel.processCommand(MainScreenCommand.RefreshData) },
                    isOnline = isOnline
                )
            }
        }
    }
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    state: MainScreenState.Content,
    innerPadding: PaddingValues,
    onCardClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = innerPadding
    ) {
        item {
            if (state.upcomingList.isNotEmpty()) {
                Subtitle(
                    text = "Upcoming"
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        item {
            AnimeCardsRow(
                animeList = state.upcomingList,
                onCardClick = onCardClick
            )
        }
        item {
            if (state.airingList.isNotEmpty()) {
                Subtitle(
                    text = "Top Airing"
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        item {
            AnimeCardsRow(
                animeList = state.airingList,
                onCardClick = onCardClick
            )
        }
        item {
            if (state.popularList.isNotEmpty()) {
                Subtitle(
                    text = "Most Popular"
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        item {
            AnimeCardsRow(
                animeList = state.popularList,
                onCardClick = onCardClick
            )
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

@Composable
private fun TopMainScreenBar(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit,
    onRefreshDataClick: () -> Unit,
    isOnline: Boolean
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Tsundoku",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 24.sp,
                fontFamily = CustomFonts.Poppins,
                fontWeight = FontWeight.ExtraBold
            )
        },
        actions = {
            RefreshIconButton(
                modifier = Modifier.size(24.dp),
                onRefreshDataClick = onRefreshDataClick,
                isOnline = isOnline
            )
            Spacer(modifier = modifier.width(16.dp))
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(CircleShape)
                    .clickable {
                        onSettingsClick()
                    },
                imageVector = Icons.Default.Settings,
                contentDescription = "Setting button",
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}




@Composable
private fun Subtitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier.padding(start = 24.dp),
        text = text,
        fontFamily = CustomFonts.Poppins,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 16.sp
    )
}

@Composable
private fun RefreshIconButton(
    modifier: Modifier = Modifier,
    onRefreshDataClick: () -> Unit,
    isOnline: Boolean
) {
    IconButton(
        modifier = modifier,
        onClick = onRefreshDataClick,
        enabled = isOnline,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary,
            disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        ),
        shape = CircleShape
    ) {
        Icon(
            modifier = modifier,
            imageVector = Icons.Default.Refresh,
            contentDescription = "Setting button"
        )
    }
}


@Composable
private fun NetworkProblemField(
    modifier: Modifier = Modifier,
    onRefreshDataClick: () -> Unit,
    isOnline: Boolean
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RefreshIconButton(
            modifier = Modifier.size(96.dp),
            onRefreshDataClick = onRefreshDataClick,
            isOnline = isOnline
        )
        if (isOnline) {
            Text(
                text = "You are back online!\nClick to refresh",
                fontFamily = CustomFonts.Poppins,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        } else {
            Text(
                text = "Check your Internet connection",
                fontFamily = CustomFonts.Poppins,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        }

    }
}