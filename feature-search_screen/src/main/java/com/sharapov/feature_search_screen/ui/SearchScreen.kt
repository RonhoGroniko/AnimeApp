@file:OptIn(ExperimentalMaterial3Api::class)

package com.sharapov.feature_search_screen.ui

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.sharapov.core_domain.entity.list.AnimeListItem
import com.sharapov.core_ui.R
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.composable.ErrorWithImage
import com.sharapov.core_ui.theme.core.BasePane
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.icons.CustomIcons
import com.sharapov.core_ui.theme.icons.Filter
import com.sharapov.feature_search_screen.domain.entity.AnimeFilter
import com.sharapov.feature_search_screen.ui.composables.EnterAlwaysTopAppBar
import com.sharapov.feature_search_screen.ui.composables.FilterModalBottomSheet
import com.sharapov.feature_search_screen.ui.model.AnimeFilterUiModel
import com.sharapov.feature_search_screen.ui.model.toUi
import kotlinx.coroutines.CoroutineScope

private const val EMPTY_QUERY = ""

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
                onClearQuery = {
                    viewModel.processCommand(
                        SearchScreenCommand.ChangeQuery(
                            EMPTY_QUERY
                        )
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    shape = FloatingActionButtonDefaults.shape
                ),
                onClick = {
                    showBottomSheet = true
                },
                contentColor = MaterialTheme.colorScheme.background,
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(imageVector = CustomIcons.Outlined.Filter, contentDescription = "Add filters")
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
                    initialFilter = currentState.data.filter.toUi(),
                    onApplyFilter = { newFilter ->
                        viewModel.processCommand(SearchScreenCommand.ApplyFilter(newFilter))
                    },
                    onDismiss = {
                        showBottomSheet = false
                    },
                    showBottomSheet = showBottomSheet
                )
            }

            is LceState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorWithImage("Бака сервер потерял записи, хех", R.drawable.confused_anime_girl)
                }
            }
            LceState.Initial -> {}
            LceState.Loading -> {}
        }
    }
}



@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    lazyPagingItems: LazyPagingItems<AnimeListItem>,
    sheetState: SheetState,
    scope: CoroutineScope,
    showBottomSheet: Boolean,
    initialFilter: AnimeFilterUiModel,
    onDismiss: () -> Unit,
    onCardClick: (Long) -> Unit,
    onApplyFilter: (AnimeFilter) -> Unit
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
        initialFilter = initialFilter,
        innerPadding = innerPadding,
        onApplyFilter = onApplyFilter,
    )
}
