package com.sharapov.core_ui.theme.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.sharapov.core_ui.theme.CustomFonts

@Composable
fun Subtitle(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit,
    fontFamily: FontFamily = CustomFonts.Poppins,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = MaterialTheme.colorScheme.secondary,
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        color = color,
        fontSize = fontSize
    )
}
