package com.sharapov.core_di

import android.content.Context
import androidx.room.Room
import com.sharapov.core_data.BuildConfig
import com.sharapov.core_data.local.database.AnimeDao
import com.sharapov.core_data.local.database.AnimeDatabase
import com.sharapov.core_data.remote.retrofit.AnimeApiService
import com.sharapov.core_data.remote.retrofit.MalInterceptor
import com.sharapov.core_data.repository.AnimeRepositoryImpl
import com.sharapov.core_domain.repository.AnimeRepository
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
            ).build()
        }

        @Provides
        @Singleton
        fun provideAnimeDao(
            database: AnimeDatabase
        ): AnimeDao {
            return database.animeDao()
        }
    }
}