package com.sharapov.feature_search_screen.composables

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.composable.FilterSubtitle
import com.sharapov.core_ui.theme.icons.ArrowDropDown
import com.sharapov.core_ui.theme.icons.ArrowDropUp
import com.sharapov.core_ui.theme.icons.Close
import com.sharapov.core_ui.theme.icons.CustomIcons
import com.sharapov.core_ui.theme.icons.Delete
import com.sharapov.core_ui.theme.icons.Done
import com.sharapov.domain_anime.entity.AnimeKind
import com.sharapov.domain_anime.entity.AnimeRating
import com.sharapov.domain_anime.entity.filter.AnimeFilter
import com.sharapov.domain_anime.entity.filter.genre.Genre
import com.sharapov.feature_search_screen.model.AnimeFilterUiModel
import com.sharapov.feature_search_screen.model.AnimeStatusUiModel
import com.sharapov.feature_search_screen.model.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FilterModalBottomSheet(
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
        var selectedStatus by remember { mutableStateOf<AnimeStatusUiModel?>(null) }
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
                        items = AnimeStatusUiModel.entries.filter { it != AnimeStatusUiModel.UNKNOWN },
                        selectedItem = selectedStatus,
                        label = { it.value },
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