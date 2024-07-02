package com.ezdev.restaurant_hours_app.core.data.mapper

import com.ezdev.restaurant_hours_app.core.data.local.RestaurantEntity
import com.ezdev.restaurant_hours_app.core.data.remote.RestaurantDto
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.navigation.Screen

fun RestaurantDto.toEntity(): RestaurantEntity = RestaurantEntity(
    name = name,
    operatingHours = operatingHours,
)

fun RestaurantEntity.toRestaurant() = Restaurant(
    name = name,
    operatingHours = operatingHours
)

fun Restaurant.toDetail() = Screen.RestaurantDetail(name = name)

