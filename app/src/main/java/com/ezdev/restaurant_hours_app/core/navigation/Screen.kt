package com.ezdev.restaurant_hours_app.core.navigation

import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class Item(
        val name: String,
        val operatingHours: String,
        val isOpening: Boolean
    ) : Screen() {
        fun toRestaurant(): Restaurant = Restaurant(name, operatingHours, isOpening)
    }
}