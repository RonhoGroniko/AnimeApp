package com.sharapov.core_data.local.dbmodel.anime_details.converters

import androidx.room.TypeConverter
import com.sharapov.core_domain.entity.details.AgeRating
import com.sharapov.core_domain.entity.details.MediaType
import com.sharapov.core_domain.entity.details.RelationType
import com.sharapov.core_domain.entity.details.Source
import com.sharapov.core_domain.entity.details.Status

object EnumConverter {

    fun <T : Enum<T>> toString(value: T?): String? = value?.name

    inline fun <reified T : Enum<T>> fromString(value: String?, default: T): T {
        if (value.isNullOrBlank()) return default
        return enumValues<T>().firstOrNull { it.name == value } ?: default
    }
}

class MediaTypeConverter {

    @TypeConverter
    fun toString(value: MediaType?): String? {
        return EnumConverter.toString(value)
    }

    @TypeConverter
    fun fromString(value: String?): MediaType =
        EnumConverter.fromString<MediaType>(value, MediaType.UNKNOWN)
}

class AgeRatingConverter {
    @TypeConverter
    fun toString(value: AgeRating?): String? =
        EnumConverter.toString(value)

    @TypeConverter
    fun fromString(value: String?): AgeRating =
        EnumConverter.fromString<AgeRating>(value, AgeRating.UNKNOWN)
}

class StatusTypeConverter {
    @TypeConverter
    fun toString(value: Status?): String? =
        EnumConverter.toString(value)

    @TypeConverter
    fun fromString(value: String?): Status =
        EnumConverter.fromString<Status>(value, Status.UNKNOWN)
}

class SourceTypeConverter {
    @TypeConverter
    fun toString(value: Source?): String? =
        EnumConverter.toString(value)

    @TypeConverter
    fun fromString(value: String?): Source =
        EnumConverter.fromString<Source>(value, Source.UNKNOWN)
}

class RelationTypeConverter {
    @TypeConverter
    fun toString(value: RelationType?): String? =
        EnumConverter.toString(value)

    @TypeConverter
    fun fromString(value: String?): RelationType =
        EnumConverter.fromString<RelationType>(value, RelationType.UNKNOWN)
}