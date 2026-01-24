@file:OptIn(ExperimentalMaterial3Api::class)

package com.sharapov.feature_search_screen


import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
import com.sharapov.core_ui.R
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.composable.ErrorWithImage
import com.sharapov.core_ui.theme.composable.FilterSubtitle
import com.sharapov.core_ui.theme.core.BasePane
import com.sharapov.core_ui.theme.core.LceState
import com.sharapov.core_ui.theme.icons.ArrowDropDown
import com.sharapov.core_ui.theme.icons.ArrowDropUp
import com.sharapov.core_ui.theme.icons.Close
import com.sharapov.core_ui.theme.icons.CustomIcons
import com.sharapov.core_ui.theme.icons.Delete
import com.sharapov.core_ui.theme.icons.Done
import com.sharapov.core_ui.theme.icons.Filter
import com.sharapov.core_ui.theme.icons.Search
import com.sharapov.domain_anime.entity.AnimeKind
import com.sharapov.domain_anime.entity.AnimeRating
import com.sharapov.domain_anime.entity.AnimeStatus
import com.sharapov.domain_anime.entity.filter.AnimeFilter
import com.sharapov.domain_anime.entity.filter.genre.Genre
import com.sharapov.domain_anime.entity.list.AnimeListItem
import com.sharapov.feature_search_screen.model.AnimeFilterUiModel
import com.sharapov.feature_search_screen.model.toEntity
import com.sharapov.feature_search_screen.model.toUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterAlwaysTopAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                query = query,
                onQueryChange = onQueryChange,
                onClearQuery = onClearQuery
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

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit
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
            if (query.isBlank()) {
                Icon(
                    imageVector = CustomIcons.Filled.Search,
                    contentDescription = "Search button",
                    tint = MaterialTheme.colorScheme.secondary
                )
            } else {
                IconButton(
                    onClick = {
                        onClearQuery()
                    },
                ) {
                    Icon(
                        imageVector = CustomIcons.Filled.Close,
                        contentDescription = "Clear query",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
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
    initialFilter: AnimeFilterUiModel,
    onApplyFilter: (AnimeFilter) -> Unit,
    onDismiss: () -> Unit
) {
    if (showBottomSheet) {

        val selectedGenres = remember { mutableStateListOf<String>() }
        var selectedStatus by remember { mutableStateOf<AnimeStatus?>(null) }
        var selectedType by remember { mutableStateOf<AnimeKind?>(null) }
        var selectedRating by remember { mutableStateOf<AnimeRating?>(null) }
        var isCensored by remember { mutableStateOf<Boolean>(false) }

        val clearEnabled =
            !(selectedGenres.isEmpty() && selectedStatus == null && selectedType == null && selectedRating == null && isCensored == initialFilter.censored)

        LaunchedEffect(Unit) {
            selectedGenres.addAll(initialFilter.genre ?: listOf())
            selectedStatus = initialFilter.status
            selectedType = initialFilter.kind
            selectedRating = initialFilter.rating
            isCensored = initialFilter.censored
        }


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
                    .padding(horizontal = 8.dp)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
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
                    Icon(
                        imageVector = CustomIcons.Filled.Close,
                        contentDescription = "Close filters"
                    )
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
                Button(
                    modifier = Modifier.padding(vertical = 2.dp),
                    enabled = clearEnabled,
                    onClick = {
                        selectedGenres.clear()
                        selectedType = null
                        selectedStatus = null
                        selectedRating = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondaryContainer)
                ) {
//                    Text(text = "Clear", fontFamily = CustomFonts.Poppins)
                    Icon(
                        imageVector = CustomIcons.Outlined.Delete,
                        contentDescription = "Close filters"
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Button(
                    modifier = Modifier.padding(vertical = 2.dp),
                    onClick = {
                        val filter = AnimeFilterUiModel(
                            genre = selectedGenres,
                            status = selectedStatus,
                            kind = selectedType,
                            rating = selectedRating,
                            censored = isCensored
                        )
                        onApplyFilter(filter.toEntity())
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondaryContainer)
                ) {
//                    Text(text = "Apply", fontFamily = CustomFonts.Poppins)
                    Icon(
                        imageVector = CustomIcons.Filled.Done,
                        contentDescription = "Apply filters"
                    )
                }
            }
            LazyColumn() {
                item {
                    GenresFilter(
                        selectedGenres = selectedGenres,
                        onToggle = { genre ->
                            if (genre in selectedGenres) {
                                selectedGenres.remove(genre)
                            } else {
                                selectedGenres.add(genre)
                            }
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    FilterPart(
                        subtitleText = "Status",
                        items = AnimeStatus.entries.filter { it != AnimeStatus.UNKNOWN },
                        selectedItem = selectedStatus,
                        label = { it.valueForUi },
                        onToggle = { status ->
                            selectedStatus = if (selectedStatus == status) null else status
                        }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    FilterPart(
                        subtitleText = "Type",
                        items = AnimeKind.entries.filter { it != AnimeKind.UNKNOWN },
                        selectedItem = selectedType,
                        label = { it.value },
                        onToggle = { type ->
                            selectedType = if (selectedType == type) null else type
                        }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    FilterPart(
                        subtitleText = "Age Rating",
                        items = AnimeRating.entries.filter { it != AnimeRating.UNKNOWN },
                        selectedItem = selectedRating,
                        label = { it.value },
                        onToggle = { rating ->
                            selectedRating = if (selectedRating == rating) null else rating
                        }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                item {
                    FilterWithSwitch(
                        text = "Censored",
                        checked = isCensored,
                        onCheck = {
                            isCensored = it
                        }
                    )
                }
                item {
                    HorizontalDivider(
                        modifier = modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun GenresFilter(
    modifier: Modifier = Modifier,
    selectedGenres: List<String>,
    onToggle: (String) -> Unit
) {
    var expandGenres by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
    ) {
        ExpandableFilterSubtitle(
            text = "Genres",
            expand = expandGenres,
            onExpandClick = { expandGenres = !expandGenres }
        )
        if (expandGenres) {
            Genre.sortedAlphabetically.forEach { sortedMap ->
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
                                onToggle(genre)
                            },
                            label = {
                                Text(
                                    text = genre,
                                    fontFamily = CustomFonts.Poppins,
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun <T> FilterPart(
    modifier: Modifier = Modifier,
    subtitleText: String,
    items: List<T>,
    selectedItem: T?,
    label: (T) -> String,
    onToggle: (T) -> Unit
) {
    Column(modifier = modifier) {
        FilterSubtitle(text = subtitleText)
        FlowRow(
            modifier = Modifier.padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items.forEach { item ->
                val selected = item == selectedItem
                FilterChip(
                    selected = selected,
                    onClick = {
                        onToggle(item)
                    },
                    label = {
                        Text(
                            text = label(item),
                            fontFamily = CustomFonts.Poppins,
                        )
                    }
                )
            }
        }
    }
}


@Composable
private fun ExpandableFilterSubtitle(
    modifier: Modifier = Modifier,
    text: String,
    expand: Boolean,
    onExpandClick: () -> Unit
) {
    Column() {
        Row(
            modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(onClick = { onExpandClick() }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterSubtitle(
                text = text,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(24.dp),
                imageVector = if (expand) CustomIcons.Filled.ArrowDropUp else CustomIcons.Filled.ArrowDropDown,
                contentDescription = if (expand) "Shrink genres" else "Expand genres",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        HorizontalDivider(
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
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

@Composable
private fun FilterWithSwitch(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    onCheck: (Boolean) -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterSubtitle(
            text = text,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        Switch(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .semantics { contentDescription = "Check $text filter" },
            checked = checked,
            onCheckedChange = { onCheck(it) },
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.secondary,
                checkedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        )

    }
}