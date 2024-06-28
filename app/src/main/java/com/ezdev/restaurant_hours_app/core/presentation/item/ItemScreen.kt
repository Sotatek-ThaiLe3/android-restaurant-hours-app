package com.ezdev.restaurant_hours_app.core.presentation.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant

@Composable
fun ItemScreen(
    modifier: Modifier = Modifier,
    viewModel: ItemViewModel = hiltViewModel()
) {
    val restaurant: Restaurant by viewModel.restaurant.collectAsStateWithLifecycle()

    ItemBody(restaurant = restaurant, modifier = modifier)
}

@Composable
private fun ItemBody(restaurant: Restaurant, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = restaurant.name)
        Text(text = restaurant.operatingHours)
        Text(text = restaurant.isOpening.toString())
        Text(text = restaurant.weeklyCalendar.toString())
    }
}