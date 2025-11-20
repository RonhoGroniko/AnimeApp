package com.sharapov.core_ui.theme

import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDate(input: String): String {
    if (input.isBlank()) return "Unknown"

    val locale = Locale.getDefault()

    return try {
        when {
            Regex("""\d{4}-\d{2}-\d{2}""").matches(input) -> {
                val date = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd", locale))
                date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", locale))
            }

            Regex("""\d{4}-\d{2}""").matches(input) -> {
                val ym = YearMonth.parse(input, DateTimeFormatter.ofPattern("yyyy-MM", locale))
                ym.format(DateTimeFormatter.ofPattern("MMMM yyyy", locale))
            }

            Regex("""\d{4}""").matches(input) -> {
                val y = Year.parse(input, DateTimeFormatter.ofPattern("yyyy", locale))
                y.format(DateTimeFormatter.ofPattern("yyyy", locale))
            }

            else -> "Unknown"
        }
    } catch (e: Exception) {
        "Unknown"
    }
}