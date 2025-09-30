package com.sharapov.core_data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sharapov.core_data.local.dbmodel.AnimeDbModel
import com.sharapov.core_data.local.dbmodel.AnimeGenreCrossRef
import com.sharapov.core_data.local.dbmodel.AnimeStudioCrossRef
import com.sharapov.core_data.local.dbmodel.GenreDbModel
import com.sharapov.core_data.local.dbmodel.StudioDbModel

@Database(
    entities = [
        AnimeDbModel::class,
        GenreDbModel::class,
        StudioDbModel::class,
        AnimeGenreCrossRef::class,
        AnimeStudioCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao
}