package com.sharapov.feature_search_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.AnimeCard
import com.sharapov.core_ui.theme.core.BasePane
import com.sharapov.core_ui.theme.core.LceState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
//    filter: AnimeFilter = AnimeFilter.All,
    viewModel: SearchViewModel = hiltViewModel(),
    onCardClick: (Long) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val state = viewModel.state.collectAsState()
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
                    contentState = currentState,
                    onCardClick = onCardClick
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
    contentState: LceState.Content<SearchScreenContent>,
    onCardClick: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(contentState.data.animeList, key = { it.id }) { anime ->
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