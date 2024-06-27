package com.ezdev.restaurant_hours_app.core.presentation.item

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.navigation.Screen

class ItemViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val item: MutableState<Restaurant> =
        mutableStateOf(savedStateHandle.toRoute<Screen.Item>().toRestaurant())
}