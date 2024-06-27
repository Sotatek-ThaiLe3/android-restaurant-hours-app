package com.ezdev.restaurant_hours_app.core.data.repository

import androidx.datastore.core.IOException
import com.ezdev.restaurant_hours_app.common.Resource
import com.ezdev.restaurant_hours_app.core.data.local.Dao
import com.ezdev.restaurant_hours_app.core.data.remote.ApiService
import com.ezdev.restaurant_hours_app.core.data.remote.RestaurantsDto
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: Dao,
    private val apiService: ApiService
) : Repository {
    override fun getRestaurants(): Flow<Resource<List<Restaurant>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteRestaurants: RestaurantsDto = apiService.fetchRestaurants()
            val restaurantsEntity = remoteRestaurants.restaurants.map { it.toEntity() }
            dao.upsertRestaurants(restaurantsEntity)
        } catch (e: HttpException) {
            emit(Resource.Error("Oops, something went wrong!"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection!"))
        } finally {
            val localRestaurants = dao.getRestaurantsStream()
            emit(Resource.Success(localRestaurants.map { it.toRestaurant() }))
        }
    }
}