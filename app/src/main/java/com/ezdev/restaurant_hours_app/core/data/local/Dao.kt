package com.ezdev.restaurant_hours_app.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM restaurants")
     fun getRestaurantsStream(): Flow<List<RestaurantEntity>>

    @Upsert
    suspend fun upsertRestaurants(restaurants: List<RestaurantEntity>)

}