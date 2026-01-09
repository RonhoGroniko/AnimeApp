@file:OptIn(ExperimentalMaterial3Api::class)

package com.sharapov.feature_search_screen


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.sharapov.domain_anime.entity.filter.genre.Genre
import com.sharapov.domain_anime.entity.list.AnimeListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                }
            ) {
                Icon(Icons.Filled.Add, "Add filters")
            }
        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding, currentState ->
        when (currentState) {
            is LceState.Content<SearchScreenContent> -> {
                SearchScreenContent(
                    innerPadding = innerPadding,
                    onCardClick = onCardClick,
                    lazyPagingItems = animeList,
                    sheetState = sheetState,
                    scope = scope,
                    onDismiss = {
                        showBottomSheet = false
                    },
                    showBottomSheet = showBottomSheet
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
    sheetState: SheetState,
    scope: CoroutineScope,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
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
    FilterModalBottomSheet(
        sheetState = sheetState,
        scope = scope,
        onDismiss = onDismiss,
        showBottomSheet = showBottomSheet,
        innerPadding = innerPadding
    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterModalBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    scope: CoroutineScope,
    showBottomSheet: Boolean,
    innerPadding: PaddingValues,
    onDismiss: () -> Unit
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = modifier
                .fillMaxHeight()
                .windowInsetsPadding(
                    WindowInsets(top = innerPadding.calculateTopPadding())
                ),
            onDismissRequest = {
                onDismiss()
            },
            dragHandle = null,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .height(IntrinsicSize.Min),
            ) {
                IconButton(
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismiss()
                            }
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close filters")
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Filters",
                        fontFamily = CustomFonts.Poppins,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            LazyColumn() {
                item {
                    GenresFilter()
                }

            }

        }
    }
}

@Composable
private fun GenresFilter(
    modifier: Modifier = Modifier
) {
    val selectedGenres = remember { mutableStateListOf<String>() }
    Column(modifier = modifier.fillMaxSize()) {
        FilterSubtitle(text = "Genres")
        Genre.sortedAlphabetically.forEach { sortedMap ->
            Column() {
                UppercaseLetterWithDivider(letter = sortedMap.key)
                FlowRow(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    sortedMap.value.forEach { genre ->
                        val selected = genre in selectedGenres
                        FilterChip(
                            selected = selected,
                            onClick = {
                                if (selected) {
                                    selectedGenres.remove(genre)
                                } else {
                                    selectedGenres.add(genre)
                                }
                            },
                            label = { Text(text = genre) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterSubtitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier.padding(start = 16.dp, top = 8.dp),
        text = text,
        fontFamily = CustomFonts.Poppins,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.secondary,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun UppercaseLetterWithDivider(
    modifier: Modifier = Modifier,
    letter: Char,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = letter.toString(),
            fontFamily = CustomFonts.Poppins,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontStyle = FontStyle.Italic
        )
        HorizontalDivider(
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

}