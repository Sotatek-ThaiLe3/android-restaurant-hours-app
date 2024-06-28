package com.ezdev.restaurant_hours_app.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "restaurants")
data class RestaurantEntity(
    @PrimaryKey
    val name: String,
    val operatingHours: String,
)


