package com.sharapov.core_ui.theme.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sharapov.core_ui.R
import com.sharapov.core_ui.theme.CustomFonts

@Composable
fun ErrorSubtitle(
    text: String,
    fontSize: TextUnit,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontFamily = CustomFonts.Poppins,
        fontWeight = fontWeight,
        color = color
    )
}

@Composable
fun ErrorWithImage(
    text: String,
    @DrawableRes id: Int
) {
    Column() {
        Image(
            painter = painterResource(id),
            contentDescription = "No rating"
        )
        ErrorSubtitle(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
    }
}