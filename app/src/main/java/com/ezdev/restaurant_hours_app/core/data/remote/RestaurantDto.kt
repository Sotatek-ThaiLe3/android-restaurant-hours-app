package com.ezdev.restaurant_hours_app.core.data.remote

import com.ezdev.restaurant_hours_app.core.data.local.RestaurantEntity

data class RestaurantDto(
    val name: String,
    val operatingHours: String
) {
    fun toEntity(): RestaurantEntity = RestaurantEntity(
        name = name,
        operatingHours = operatingHours,
        isOpening = true
    )
}