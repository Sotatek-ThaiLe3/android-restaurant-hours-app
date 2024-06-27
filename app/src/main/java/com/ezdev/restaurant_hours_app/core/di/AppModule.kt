package com.ezdev.restaurant_hours_app.core.di

import android.content.Context
import androidx.room.Room
import com.ezdev.restaurant_hours_app.core.data.local.AppDatabase
import com.ezdev.restaurant_hours_app.core.data.local.Dao
import com.ezdev.restaurant_hours_app.core.data.remote.ApiService
import com.ezdev.restaurant_hours_app.core.data.repository.RepositoryImpl
import com.ezdev.restaurant_hours_app.core.domain.repository.Repository
import com.ezdev.restaurant_hours_app.core.domain.usecase.GetRestaurantHoursUseCase
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    companion object {
        @Singleton
        @Provides
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            ).build()

        @Singleton
        @Provides
        fun provideDao(appDatabase: AppDatabase): Dao =
            appDatabase.dao()

        @Singleton
        @Provides
        fun provideAppApi(): Retrofit =
            Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()

        @Singleton
        @Provides
        fun provideApiService(
            restaurantAppApi: Retrofit
        ): ApiService =
            restaurantAppApi.create(ApiService::class.java)

        @Singleton
        @Provides
        fun provideGetRestaurantHoursUseCase(repository: Repository): GetRestaurantHoursUseCase =
            GetRestaurantHoursUseCase(repository)
    }

    @Singleton
    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}