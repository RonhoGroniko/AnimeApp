package com.sharapov.feature_recommendations_screen.di

import com.sharapov.feature_recommendations_screen.data.remote.RecommendationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RecommendationModule {

    companion object {

        @Provides
        @Singleton
        fun provideRecommendationApiService(
            retrofit: Retrofit
        ): RecommendationApiService {
            return retrofit.create(RecommendationApiService::class.java)
        }
    }
}