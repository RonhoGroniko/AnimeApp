package com.sharapov.feature_main_screen.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.icons.CustomIcons
import com.sharapov.core_ui.theme.icons.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopMainScreenBar(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit,
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
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(CircleShape)
                    .clickable {
                        onSettingsClick()
                    },
                imageVector = CustomIcons.Outlined.Settings,
                contentDescription = "Setting button",
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}