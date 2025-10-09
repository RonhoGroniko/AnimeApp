package com.sharapov.feature_search_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.composable.BasePane

@Composable
fun SearchScreen(
    genre: String = "",
    viewModel: SearchViewModel = hiltViewModel { factory: SearchViewModel.Factory ->
        factory.create(genre)
    },
    onCardClick: (Int) -> Unit
) {
    BasePane { innerPadding ->

        val state = viewModel.state.collectAsState()
        when (val currentState = state.value) {
            is SearchScreenState.Content -> {
                SearchScreenContent(
                    innerPadding = innerPadding,
                    state = currentState,
                    onQueryChange = {
                        viewModel.processCommand(SearchScreenCommand.ChangeQuery(it))
                    },
                    onSearchCLick = {
                        viewModel.processCommand(SearchScreenCommand.Search(it))
                    },
                    onCardClick = onCardClick
                )
            }

            is SearchScreenState.Error -> {

            }

            SearchScreenState.Initial -> {

            }

            SearchScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    state: SearchScreenState.Content,
    onQueryChange: (String) -> Unit,
    onSearchCLick: (String) -> Unit,
    onCardClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth(),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            SearchBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                query = state.query,
                onQueryChange = onQueryChange,
                onSearchClick = onSearchCLick
            )
        }

        items(state.animeList, key = { it.id }) { anime ->
            AnimeCard(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth()
                    .aspectRatio(0.72f),
                anime = anime,
                onCardClick = onCardClick
            )
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            ),
        value = query,
        textStyle = TextStyle(
            fontFamily = CustomFonts.Poppins,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            fontStyle = FontStyle.Italic
        ),
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = "Search",
                fontFamily = CustomFonts.Poppins,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontStyle = FontStyle.Italic
            )
        },
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = { onSearchClick(query) },
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search button",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(8.dp)
    )
}
