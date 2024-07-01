package com.ezdev.restaurant_hours_app.core.data.repository

import com.ezdev.restaurant_hours_app.core.data.local.RestaurantDao
import com.ezdev.restaurant_hours_app.core.data.mapper.toEntity
import com.ezdev.restaurant_hours_app.core.data.mapper.toRestaurant
import com.ezdev.restaurant_hours_app.core.data.remote.RestaurantApiService
import com.ezdev.restaurant_hours_app.core.data.remote.RestaurantsDto
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantDao: RestaurantDao,
    private val restaurantApiService: RestaurantApiService,
) : RestaurantRepository {
    override suspend fun loadRestaurants() {
        val remoteRestaurants: RestaurantsDto = restaurantApiService.fetchRestaurants()
        val restaurantsEntity = remoteRestaurants.restaurants.map { it.toEntity() }
        restaurantDao.upsertRestaurants(restaurantsEntity)
    }

    override fun getRestaurants(): Flow<List<Restaurant>> =
        restaurantDao.getRestaurantsStream().map { list -> list.map { entity -> entity.toRestaurant() } }

    override fun getRestaurant(name: String): Flow<Restaurant> =
        restaurantDao.getRestaurantStream(name).map { it?.toRestaurant() ?: Restaurant() }


}