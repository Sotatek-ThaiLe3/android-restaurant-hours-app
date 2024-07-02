package com.ezdev.restaurant_hours_app.core.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ezdev.restaurant_hours_app.core.data.mapper.toDetail
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant
import com.ezdev.restaurant_hours_app.core.navigation.Screen
import com.ezdev.restaurant_hours_app.ui.SwipeRefreshLayout
import com.ezdev.restaurant_hours_app.ui.theme.RestaurantHoursTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToItem: (Screen.RestaurantDetail) -> Unit,
    modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState: HomeUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isRefreshing = viewModel.isRefreshing

    println("uiState $uiState")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Restaurant Hours") })
        },
        modifier = modifier
    ) { innerPadding ->
        HomeBody(
            uiState = uiState,
            isRefreshing = isRefreshing,
            onLoadRestaurants = viewModel::loadRestaurants,
            onNavigateToItem = onNavigateToItem,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}


@Composable
private fun HomeBody(
    uiState: HomeUiState,
    isRefreshing: Boolean,
    onLoadRestaurants: () -> Unit,
    onNavigateToItem: (Screen.RestaurantDetail) -> Unit,
    modifier: Modifier = Modifier
) {
    //TODO UI status
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.errorMessage.isNotBlank() -> Text(text = uiState.errorMessage)
            uiState.restaurants.isEmpty() -> Text(text = "No data.")
            else -> {
                // TODO Restaurant List
                RestaurantList(
                    restaurants = uiState.restaurants,
                    isRefreshing = isRefreshing,
                    onLoadRestaurants = onLoadRestaurants,
                    onNavigateToItem = onNavigateToItem,
                )
            }
        }
    }
}

@Composable
private fun RestaurantList(
    restaurants: List<Restaurant>,
    isRefreshing: Boolean,
    onLoadRestaurants: () -> Unit,
    onNavigateToItem: (Screen.RestaurantDetail) -> Unit,
    modifier: Modifier = Modifier
) {
    SwipeRefreshLayout(isRefreshing = isRefreshing, onRefresh = onLoadRestaurants) {
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
}

@Composable
private fun RestaurantItem(
    restaurant: Restaurant,
    onNavigateToItem: (Screen.RestaurantDetail) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .border(3.dp, Color.Black.copy(alpha = 0.5f), CircleShape)
                .background(
                    if (restaurant.isOpening) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    CircleShape
                )
        )
        HorizontalDivider(modifier = Modifier.width(16.dp))
        Card(
            onClick = { onNavigateToItem(restaurant.toDetail()) },
//            colors = CardDefaults.cardColors(containerColor = if (restaurant.isOpening) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer),
            modifier = Modifier.weight(1f)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(text = restaurant.name, style = MaterialTheme.typography.titleLarge)
                Text(text = restaurant.operatingHours)
                Text(
                    text = if (restaurant.isOpening) "Opening" else "Closed",
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        HorizontalDivider(modifier = Modifier.width(24.dp))
    }
}

@PreviewLightDark
@Composable
private fun RestaurantItemPreview() {
    RestaurantHoursTheme {
        Surface {
            RestaurantItem(restaurant = Restaurant(), onNavigateToItem = {})
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    RestaurantHoursTheme {
        Surface {
            HomeBody(
                uiState = HomeUiState(),
                isRefreshing = false,
                onLoadRestaurants = {},
                onNavigateToItem = {})
        }
    }
}