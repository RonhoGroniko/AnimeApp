package com.sharapov.feature_details_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharapov.core_ui.theme.CustomFonts
import com.sharapov.core_ui.theme.icons.ArrowBack
import com.sharapov.core_ui.theme.icons.CustomIcons


@Composable
internal fun OverlayTopAppBar(
    title: String,
    alpha: Float,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .background(MaterialTheme.colorScheme.background.copy(alpha = alpha)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            enabled = alpha > 0.001f
        ) {
            Icon(
                imageVector = CustomIcons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
            )
        }
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.secondary.copy(alpha = alpha),
            fontFamily = CustomFonts.Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
    }
}
