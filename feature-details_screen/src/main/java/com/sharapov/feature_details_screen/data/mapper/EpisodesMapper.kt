package com.sharapov.feature_details_screen.data.mapper

fun episodesToUi(aired: Int, total: Int) : String {
    return when {
        aired != 0 && total == 0 -> String.format("%s/-", aired)
        aired == 0 && total != 0 -> total.toString()
        aired == 0 && total == 0 -> "Unknown"
        else -> String.format("%s/%s", aired, total)
    }
}