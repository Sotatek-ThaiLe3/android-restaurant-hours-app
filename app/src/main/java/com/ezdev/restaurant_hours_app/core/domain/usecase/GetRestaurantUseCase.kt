package com.ezdev.restaurant_hours_app.core.domain.usecase

import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRestaurantUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(name: String): Flow<Restaurant> = repository.getRestaurant(name)

}