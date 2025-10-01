package com.sharapov.core_ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.sharapov.core_ui.R

object CustomFonts {

    val Poppins = FontFamily(
        Font(R.font.poppins_regular, weight = FontWeight.Normal),
        Font(R.font.poppins_semibold, weight = FontWeight.SemiBold),
        Font(R.font.poppins_extrabold, weight = FontWeight.ExtraBold)
    )
}

