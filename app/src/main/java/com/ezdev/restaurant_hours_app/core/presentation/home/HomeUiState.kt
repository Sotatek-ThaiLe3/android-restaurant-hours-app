package com.ezdev.restaurant_hours_app.core.presentation.home

import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant

data class HomeUiState(
    val isLoading: Boolean = false,
    val restaurants: List<Restaurant> = emptyList(),
    val errorMessage: String = ""
)