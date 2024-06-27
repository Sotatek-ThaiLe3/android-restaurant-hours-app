package com.ezdev.restaurant_hours_app.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface Dao {
    @Query("SELECT * FROM restaurants")
    suspend fun getRestaurantsStream(): List<RestaurantEntity>

    @Upsert
    suspend fun upsertRestaurants(restaurants: List<RestaurantEntity>)

}