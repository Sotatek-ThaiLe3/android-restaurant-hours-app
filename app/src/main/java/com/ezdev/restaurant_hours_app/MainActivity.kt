package com.ezdev.restaurant_hours_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ezdev.restaurant_hours_app.core.navigation.AppNavigation
import com.ezdev.restaurant_hours_app.ui.theme.RestaurantHoursTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantHoursTheme(darkTheme = false) {
                Surface {
                    AppNavigation(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}
