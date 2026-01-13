package com.sharapov.database_anime

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sharapov.database_anime.dao.AnimeDao
import com.sharapov.database_anime.model.AnimeDbModel

@Database(
    entities = [AnimeDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao() : AnimeDao
}