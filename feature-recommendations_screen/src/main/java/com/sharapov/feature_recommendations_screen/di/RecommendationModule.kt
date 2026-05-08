package com.sharapov.feature_recommendations_screen.di

import com.sharapov.feature_recommendations_screen.data.remote.RecommendationApiService
import com.sharapov.feature_recommendations_screen.data.repository.RecommendationRepositoryImpl
import com.sharapov.feature_recommendations_screen.domain.repository.RecommendationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RecommendationModule {

    @Binds
    @Singleton
    fun bindRecommendationRepository(impl: RecommendationRepositoryImpl): RecommendationRepository

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