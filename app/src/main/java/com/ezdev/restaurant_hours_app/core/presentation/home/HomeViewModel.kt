package com.ezdev.restaurant_hours_app.core.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezdev.restaurant_hours_app.common.Resource
import com.ezdev.restaurant_hours_app.core.domain.usecase.GetRestaurantHoursUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRestaurantHoursUseCase: GetRestaurantHoursUseCase
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = getRestaurantHoursUseCase()
        .map { result ->
            when (result) {
                is Resource.Error -> HomeUiState(
                    errorMessage = result.message ?: "Unknown error"
                )

                is Resource.Loading -> {
                    delay(LOADING_MILLIS)
                    HomeUiState(isLoading = true)
                }

                is Resource.Success -> HomeUiState(
                    restaurants = result.data ?: emptyList()
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = HomeUiState(),
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS)
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        private const val LOADING_MILLIS = 500L
    }

    fun loadRestaurants() {
        getRestaurantHoursUseCase(newRequest = true)
    }

}