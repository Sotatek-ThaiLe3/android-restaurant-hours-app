package com.ezdev.restaurant_hours_app.core.presentation.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.domain.usecase.GetRestaurantUseCase
import com.ezdev.restaurant_hours_app.core.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getRestaurantUseCase: GetRestaurantUseCase
) : ViewModel() {
    private val name: String = checkNotNull(savedStateHandle.toRoute<Screen.Item>().name)

    val restaurant: StateFlow<Restaurant> =
        getRestaurantUseCase(name)
            .stateIn(
                scope = viewModelScope,
                initialValue = Restaurant(),
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS)
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}