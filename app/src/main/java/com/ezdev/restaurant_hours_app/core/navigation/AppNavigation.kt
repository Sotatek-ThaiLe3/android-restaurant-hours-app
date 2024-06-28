package com.ezdev.restaurant_hours_app.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.ezdev.restaurant_hours_app.core.presentation.home.HomeScreen
import com.ezdev.restaurant_hours_app.core.presentation.item.ItemScreen


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    startDestination: Screen = Screen.Home,
    navController: NavHostController = rememberNavController()
) {
    val navGraph = navController.createGraph(startDestination) {
        composable<Screen.Home> {
            HomeScreen(onNavigateToItem = {
                navController.navigate(it)
            })
        }
        composable<Screen.Item> { ItemScreen() }
    }

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            graph = navGraph,
            modifier = Modifier.padding(innerPadding)
        )
    }
}