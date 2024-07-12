package com.ezdev.restaurant_hours_app.common

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

val NavController.canGoBack: Boolean
    get() = currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

fun NavController.navigateBack() {
    if (canGoBack) {
        popBackStack()
    }
}