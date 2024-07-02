package com.ezdev.restaurant_hours_app.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.ezdev.restaurant_hours_app.core.presentation.home.HomeScreen
import com.ezdev.restaurant_hours_app.core.presentation.restaurant_detail.ItemScreen


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
        composable<Screen.RestaurantDetail> { ItemScreen(onNavigateBack = { navController.navigateBack() }) }
    }


    NavHost(
        navController = navController,
        graph = navGraph,
        modifier = modifier
    )
}

val NavController.canGoBack: Boolean
    get() = currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

fun NavController.navigateBack() {
    if (canGoBack) {
        popBackStack()
    }
}

