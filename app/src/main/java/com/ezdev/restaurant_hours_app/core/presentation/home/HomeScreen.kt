package com.ezdev.restaurant_hours_app.core.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ezdev.restaurant_hours_app.core.data.mapper.toItem
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.navigation.Screen
import com.ezdev.restaurant_hours_app.ui.theme.RestaurantHoursTheme

@Composable
fun HomeScreen(
    onNavigateToItem: (Screen.Item) -> Unit,
    modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState: HomeUiState by viewModel.uiState

    HomeBody(
        uiState = uiState,
        onNavigateToItem = onNavigateToItem,
        modifier = modifier
    )
}

@Composable
private fun HomeBody(
    uiState: HomeUiState,
    onNavigateToItem: (Screen.Item) -> Unit,
    modifier: Modifier = Modifier
) {
    //TODO UI status
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.errorMessage.isNotBlank() -> Text(text = uiState.errorMessage)
            uiState.restaurants.isEmpty() -> Text(text = "No data.")
            else -> {
                // TODO Restaurant List
                RestaurantList(
                    restaurants = uiState.restaurants,
                    onNavigateToItem = onNavigateToItem,
                )
            }
        }
    }
}

@Composable
private fun RestaurantList(
    restaurants: List<Restaurant>,
    onNavigateToItem: (Screen.Item) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        items(restaurants, key = { it.name }) { restaurant ->
            RestaurantItem(restaurant = restaurant, onNavigateToItem = onNavigateToItem)
        }
    }
}

@Composable
private fun RestaurantItem(
    restaurant: Restaurant,
    onNavigateToItem: (Screen.Item) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onNavigateToItem(restaurant.toItem()) },
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 32.dp, horizontal = 16.dp)
        ) {
            Text(text = restaurant.name)
            Text(
                text = if (restaurant.isOpening) "Open" else "Closed",
                color = if (restaurant.isOpening) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    RestaurantHoursTheme {
        Surface {
            HomeBody(uiState = HomeUiState(), onNavigateToItem = {})
        }
    }
}