package com.sharapov.feature_details_screen.data.mapper

fun String.formatToUrl(): String {
    return if (this.startsWith("//")) {
        "https:$this"
    } else {
        this
    }
}

fun String?.formatNumberNameToEmpty(): String {
    return this?.let {
        if (this.all { it.isDigit() }) {
            ""
        } else {
            this
        }
    } ?: ""
}