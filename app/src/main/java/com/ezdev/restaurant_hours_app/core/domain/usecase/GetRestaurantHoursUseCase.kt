package com.ezdev.restaurant_hours_app.core.domain.usecase

import com.ezdev.restaurant_hours_app.common.Resource
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetRestaurantHoursUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(newRequest: Boolean = false): Flow<Resource<List<Restaurant>>> = flow {
        emit(Resource.Loading())

        try {
            repository.getRestaurants().collect { restaurants ->
                if (restaurants.isEmpty() || newRequest) {
                    repository.loadRestaurants()
                }
                emit(Resource.Success(restaurants))

            }
        } catch (e: retrofit2.HttpException) {
            emit(Resource.Error("Oops, something went wrong!"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection!"))
        }
    }
}