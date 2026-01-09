package com.sharapov.feature_search_screen


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.core.BasePane
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.domain_anime.entity.filter.AnimeFilter
import com.sharapov.domain_anime.entity.list.AnimeListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    filter: AnimeFilter = AnimeFilter(),
    viewModel: SearchViewModel = hiltViewModel { factory: SearchViewModel.Factory ->
        factory.create(filter)
    },
    onCardClick: (Long) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val state = viewModel.state.collectAsState()
    val animeList = viewModel.animePagingFlow.collectAsLazyPagingItems()

    val refreshError = animeList.loadState.refresh as? LoadState.Error

    LaunchedEffect(refreshError) {
        if (refreshError != null && animeList.itemCount == 0) {
            viewModel.errorLoadStateToLce(refreshError.error)
        }
    }

    BasePane(
        lceState = state.value,
        topBar = {
            EnterAlwaysTopAppBar(
                query = (state.value as? LceState.Content)?.data?.query.orEmpty(),
                onQueryChange = { viewModel.processCommand(SearchScreenCommand.ChangeQuery(it)) },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding, currentState ->
        when (currentState) {
            is LceState.Content<SearchScreenContent> -> {
                SearchScreenContent(
                    innerPadding = innerPadding,
                    onCardClick = onCardClick,
                    lazyPagingItems = animeList
                )
            }

            is LceState.Error -> {}
            LceState.Initial -> {}
            LceState.Loading -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterAlwaysTopAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                query = query,
                onQueryChange = onQueryChange
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    lazyPagingItems: LazyPagingItems<AnimeListItem>,
    onCardClick: (Long) -> Unit
) {
    if (lazyPagingItems.loadState.hasError && lazyPagingItems.itemCount != 0) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            Toast.makeText(context, "Error downloading", Toast.LENGTH_LONG).show()
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(lazyPagingItems.itemCount, key = lazyPagingItems.itemKey { it.id }) { index ->
            val anime = lazyPagingItems[index]
            anime?.let {
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
    when (lazyPagingItems.loadState.append) {
        is LoadState.Loading -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }

        else -> Unit
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
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
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search button",
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.inversePrimary,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.inversePrimary
        ),
        shape = RoundedCornerShape(8.dp)
    )
}