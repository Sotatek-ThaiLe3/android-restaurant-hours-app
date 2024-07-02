package com.ezdev.restaurant_hours_app.core.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezdev.restaurant_hours_app.common.Resource
import com.ezdev.restaurant_hours_app.core.domain.usecase.GetRestaurantHoursUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRestaurantHoursUseCase: GetRestaurantHoursUseCase
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = getRestaurantHoursUseCase()
        .map { result ->
            when (result) {
                is Resource.Loading ->
                    HomeUiState(isLoading = true)

                is Resource.Error ->
                    HomeUiState(errorMessage = result.message ?: "Unknown error")


                is Resource.Success ->
                    HomeUiState(restaurants = result.data ?: emptyList())
            }
        }
        .onEach {
            if(it.isLoading) {
                delay(LOADING_MILLIS)
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = HomeUiState(),
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS)
        )

    var isRefreshing by mutableStateOf(false)
        private set

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        private const val LOADING_MILLIS = 1_000L
    }

    fun loadRestaurants() {
        viewModelScope.launch {
            isRefreshing = true
            getRestaurantHoursUseCase(newRequest = true).launchIn(this)
            delay(LOADING_MILLIS)
            isRefreshing = false
        }
    }

}