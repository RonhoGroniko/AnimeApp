package com.sharapov.network_anime.mapper

import java.time.Instant as JInstant
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime
import kotlin.time.Instant as KInstant
import java.time.LocalDate


@OptIn(ExperimentalTime::class)
fun Any?.toIsoString(): String {
    if (this == null) return ""

    return try {
        when (this) {
            is String -> {
                try {
                    JInstant.parse(this).toString()
                } catch (_: Exception) {
                    try {
                        OffsetDateTime.parse(this).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    } catch (_: Exception) {
                        try {
                            ZonedDateTime.parse(this).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                        } catch (_: Exception) {
                            try {
                                LocalDateTime.parse(this).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                            } catch (_: Exception) {
                                this
                            }
                        }
                    }
                }
            }
            is JInstant -> DateTimeFormatter.ISO_INSTANT.format(this)
            is KInstant -> this.toString()
            is OffsetDateTime -> this.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            is ZonedDateTime -> this.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            is LocalDateTime -> this.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            else -> this.toString()
        }
    } catch (e: Exception) {
        ""
    }
}

fun Any?.toIsoDateString(): String {
    if (this == null) return ""

    return when (this) {

        is String -> {
            try {
                LocalDate.parse(this).format(DateTimeFormatter.ISO_LOCAL_DATE)
            } catch (_: Exception) {
                this
            }
        }

        is LocalDate ->
            this.format(DateTimeFormatter.ISO_LOCAL_DATE)

        else -> this.toString()
    }
}