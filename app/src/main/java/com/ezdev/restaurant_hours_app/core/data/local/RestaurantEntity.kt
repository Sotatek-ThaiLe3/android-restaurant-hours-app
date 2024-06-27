package com.ezdev.restaurant_hours_app.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant

@Entity(tableName = "restaurants")
data class RestaurantEntity(
    @PrimaryKey
    val name: String,
    val operatingHours: String,
    val isOpening: Boolean
) {
    fun toRestaurant(): Restaurant =
        Restaurant(name = name, operatingHours = operatingHours, isOpening = isOpening)
}