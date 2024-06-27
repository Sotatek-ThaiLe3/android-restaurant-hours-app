package com.ezdev.restaurant_hours_app.core.data.repository

import com.ezdev.restaurant_hours_app.core.data.local.Dao
import com.ezdev.restaurant_hours_app.core.data.remote.ApiService
import com.ezdev.restaurant_hours_app.core.data.remote.RestaurantsDto
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: Dao,
    private val apiService: ApiService
) : Repository {
    override suspend fun loadRestaurants() {
        val remoteRestaurants: RestaurantsDto = apiService.fetchRestaurants()
        val restaurantsEntity = remoteRestaurants.restaurants.map { it.toEntity() }
        dao.upsertRestaurants(restaurantsEntity)
    }

    override fun getRestaurants(): Flow<List<Restaurant>> =
        dao.getRestaurantsStream().map { list -> list.map { entity -> entity.toRestaurant() } }

}