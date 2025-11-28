package com.sharapov.database_anime.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AnimeDatabaseModule {

//    @Provides
//    @Singleton
//    fun provideAnimeDatabase(
//        @ApplicationContext context: Context
//    ): AnimeDatabase {
//        return Room.databaseBuilder(
//            context = context,
//            klass = AnimeDatabase::class.java,
//            name = "anime.db"
//        ).fallbackToDestructiveMigration(true)
//            .build()
//    }
}