package com.ezdev.restaurant_hours_app.core.domain.model

import com.ezdev.restaurant_hours_app.core.navigation.Screen


data class Restaurant(
    val name: String,
    val operatingHours: String,
    val isOpening: Boolean
) {
    fun toItem(): Screen.Item = Screen.Item(
        name, operatingHours, isOpening
    )
}