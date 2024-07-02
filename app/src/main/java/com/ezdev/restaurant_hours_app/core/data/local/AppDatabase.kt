package com.ezdev.restaurant_hours_app.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RestaurantEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun restaurantDao(): RestaurantDao

    companion object {
        const val DATABASE_NAME = "restaurant_hours.db"
    }
}