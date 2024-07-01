package com.ezdev.restaurant_hours_app.core.di

import android.content.Context
import androidx.room.Room
import com.ezdev.restaurant_hours_app.connectivity_observer.ConnectivityObserver
import com.ezdev.restaurant_hours_app.connectivity_observer.NetworkConnectivityObserver
import com.ezdev.restaurant_hours_app.core.data.local.AppDatabase
import com.ezdev.restaurant_hours_app.core.data.local.RestaurantDao
import com.ezdev.restaurant_hours_app.core.data.remote.RestaurantApiService
import com.ezdev.restaurant_hours_app.core.data.repository.RestaurantRepositoryImpl
import com.ezdev.restaurant_hours_app.core.domain.repository.RestaurantRepository
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
        fun provideRestaurantDao(appDatabase: AppDatabase): RestaurantDao =
            appDatabase.restaurantDao()

        @Singleton
        @Provides
        fun provideAppApi(): Retrofit =
            Retrofit.Builder()
                .baseUrl(RestaurantApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()

        @Singleton
        @Provides
        fun provideRestaurantApiService(
            appApi: Retrofit
        ): RestaurantApiService =
            appApi.create(RestaurantApiService::class.java)

        @Singleton
        @Provides
        fun provideGetRestaurantHoursUseCase(
            restaurantRepository: RestaurantRepository,
            connectivityObserver: ConnectivityObserver
        ): GetRestaurantHoursUseCase =
            GetRestaurantHoursUseCase(restaurantRepository, connectivityObserver)
    }

    @Singleton
    @Binds
    abstract fun bindRestaurantRepository(restaurantRepositoryImpl: RestaurantRepositoryImpl): RestaurantRepository

    @Singleton
    @Binds
    abstract fun bindConnectivityObserver(networkConnectivityObserver: NetworkConnectivityObserver): ConnectivityObserver
}