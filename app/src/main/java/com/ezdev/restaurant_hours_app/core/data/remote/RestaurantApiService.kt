package com.ezdev.restaurant_hours_app.core.data.remote

import retrofit2.http.GET

interface RestaurantApiService {
    @GET("homework")
    suspend fun fetchRestaurants(): RestaurantsDto

    companion object {
        const val BASE_URL = "https://setel.axzae.com/"
    }
}