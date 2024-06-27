package com.ezdev.restaurant_hours_app.core.domain.repository

import com.ezdev.restaurant_hours_app.common.Resource
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun loadRestaurants()
    fun getRestaurants(): Flow<List<Restaurant>>
}