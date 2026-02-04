package com.sharapov.feature_favorites_screen.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharapov.core_ui.theme.CustomFonts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopFavoritesAppBar() {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Favorites",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 24.sp,
                fontFamily = CustomFonts.Poppins,
                fontWeight = FontWeight.ExtraBold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}