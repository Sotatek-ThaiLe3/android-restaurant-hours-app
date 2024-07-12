package com.ezdev.restaurant_hours_app.core.domain.usecase

import com.ezdev.restaurant_hours_app.common.Resource
import com.ezdev.restaurant_hours_app.connectivity.ConnectivityObserver
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRestaurantHoursUseCase @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
    private val connectivityObserver: ConnectivityObserver
) {
    operator fun invoke(newRequest: Boolean = false): Flow<Resource<List<Restaurant>>> = flow {
        emit(Resource.Loading())

        combine(
            restaurantRepository.getRestaurants(),
            connectivityObserver.observe()
        ) { restaurants, status ->
            if (status != ConnectivityObserver.Status.AVAILABLE) {
                return@combine restaurants
            }

            if (restaurants.isEmpty() || newRequest) {
                restaurantRepository.loadRestaurants()
            }

            restaurants
        }.catch { e ->
            when (e) {
                is HttpException -> emit(Resource.Error("Oops, something went wrong!"))
                is IOException -> emit(Resource.Error("Couldn't reach server, check your internet connection!"))
            }
        }.collect {
            emit(Resource.Success(it))
        }

    }
}