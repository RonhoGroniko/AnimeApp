package com.sharapov.core_di

import android.content.Context
import androidx.room.Room
import com.sharapov.network_anime.retrofit.AnimeApiService
import com.sharapov.network_anime.retrofit.MalInterceptor
import com.sharapov.domain_anime.repository.AnimeRepository
import com.sharapov.data_anime.repository.AnimeRepositoryImpl
import com.sharapov.database_anime.dao.AnimeCoreDao
import com.sharapov.database_anime.AnimeDatabase
import com.sharapov.database_anime.dao.AnimeDetailsDao
import com.sharapov.database_anime.dao.AnimeListDao
import com.sharapov.database_anime.dao.AnimeSearchDao
import com.sharapov.network_anime.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindRepository(impl: AnimeRepositoryImpl): AnimeRepository

    companion object {

        @Provides
        @Singleton
        @MalClientId
        fun provideClientId() = BuildConfig.MAL_CLIENT_ID

        @Provides
        @Singleton
        fun provideMalInterceptor(
            @MalClientId clientId: String
        ): Interceptor {
            return MalInterceptor(clientId)
        }

        @Provides
        @Singleton
        fun provideOkHttp(
            interceptor: Interceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .callTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level =
                        HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }

        @Provides
        @Singleton
        fun provideJson(): Json {
            return Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
        }

        @Provides
        @Singleton
        fun provideConverterFactory(json: Json): Converter.Factory {
            return json.asConverterFactory("application/json".toMediaType())
        }

        @Provides
        @Singleton
        fun provideRetrofit(
            converter: Converter.Factory,
            okHttpClient: OkHttpClient
        ): Retrofit {
            return Retrofit.Builder().baseUrl("https://api.myanimelist.net/v2/")
                .client(okHttpClient)
                .addConverterFactory(converter)
                .build()
        }

        @Provides
        @Singleton
        fun provideAnimeApiService(
            retrofit: Retrofit
        ): AnimeApiService {
            return retrofit.create(AnimeApiService::class.java)
        }

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
        fun provideAnimeListDao(
            database: AnimeDatabase
        ): AnimeListDao {
            return database.animeListDao()
        }

        @Provides
        @Singleton
        fun provideAnimeDetailsDao(
            database: AnimeDatabase
        ): AnimeDetailsDao {
            return database.animeDetailsDao()
        }

        @Provides
        @Singleton
        fun provideAnimeCoreDao(
            database: AnimeDatabase
        ): AnimeCoreDao {
            return database.animeCoreDao()
        }

        @Provides
        @Singleton
        fun provideAnimeSearchDao(
            database: AnimeDatabase
        ): AnimeSearchDao {
            return database.animeSearchDao()
        }
    }
}