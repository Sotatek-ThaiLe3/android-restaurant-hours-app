package com.ezdev.restaurant_hours_app.core.domain.repository

import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    suspend fun loadRestaurants()
    fun getRestaurants(): Flow<List<Restaurant>>
    fun getRestaurant(name: String): Flow<Restaurant>
}