package com.ezdev.restaurant_hours_app.connectivity

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ConnectivityModule {
    @Singleton
    @Binds
    fun bindConnectivityObserver(networkConnectivityObserver: NetworkConnectivityObserver): ConnectivityObserver
}