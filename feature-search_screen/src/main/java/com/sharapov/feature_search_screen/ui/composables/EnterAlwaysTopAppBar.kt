package com.sharapov.feature_search_screen.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.icons.Close
import com.sharapov.core_ui.theme.icons.CustomIcons
import com.sharapov.core_ui.theme.icons.Search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EnterAlwaysTopAppBar(
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
            IconButton(
                onClick = {
                    if (query.isNotBlank()) {
                        onClearQuery()
                    }
                },
                enabled = query.isNotBlank()
            ) {
                Icon(
                    imageVector = if (query.isBlank()) CustomIcons.Filled.Search else CustomIcons.Filled.Close,
                    contentDescription = if (query.isBlank()) "Search icon" else "Clear query",
                    tint = MaterialTheme.colorScheme.secondary
                )
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