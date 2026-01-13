package com.sharapov.database_anime.di

import android.content.Context
import androidx.room.Room
import com.sharapov.database_anime.AnimeDatabase
import com.sharapov.database_anime.dao.AnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnimeDatabaseModule {

    @Provides
    @Singleton
    fun provideAnimeDatabase(
        @ApplicationContext context: Context
    ): AnimeDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AnimeDatabase::class.java,
            name = "anime.db"
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideAnimeDao(
        db: AnimeDatabase
    ): AnimeDao {
        return db.animeDao()
    }
}