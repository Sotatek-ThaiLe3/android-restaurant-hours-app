package com.ezdev.restaurant_hours_app.core.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class RestaurantDetail(
        val name: String,
    ) : Screen()
}