package com.ezdev.restaurant_hours_app.core.presentation.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezdev.restaurant_hours_app.common.Resource
import com.ezdev.restaurant_hours_app.core.domain.usecase.GetRestaurantHoursUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRestaurantHoursUseCase: GetRestaurantHoursUseCase
) : ViewModel() {
    private val _uiState: MutableState<HomeUiState> = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    init {
        loadRestaurants()
    }

    private fun loadRestaurants() {
        _uiState.value = HomeUiState(isLoading = true)

        viewModelScope.launch {
            delay(1000L)
            getRestaurantHoursUseCase().onEach { result ->
                _uiState.value = when (result) {
                    is Resource.Error -> HomeUiState(
                        errorMessage = result.message ?: "Unknown error"
                    )

                    is Resource.Loading -> HomeUiState(isLoading = true)
                    is Resource.Success -> HomeUiState(
                        restaurants = result.data ?: emptyList()
                    )
                }
            }.launchIn(this)
        }
    }
}