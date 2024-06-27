package com.ezdev.restaurant_hours_app.core.data.remote

data class RestaurantsDto(
    val timestamp: Long,
    val restaurants: List<RestaurantDto>
)