package com.ezdev.restaurant_hours_app.core.presentation.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ezdev.restaurant_hours_app.R
import com.ezdev.restaurant_hours_app.core.domain.model.Restaurant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ItemViewModel = hiltViewModel()
) {
    val restaurant: Restaurant by viewModel.restaurant.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = MaterialTheme.typography.displayLarge.fontSize
                            )
                        ) {
                            append(restaurant.name[0])
                        }
                        append(restaurant.name.substring(1))
                    }, textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                },
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ItemBody(restaurant = restaurant, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
private fun ItemBody(restaurant: Restaurant, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        WeeklyCalendarList(weeklyCalendar = restaurant.weeklyCalendar)
    }
}

@Composable
fun WeeklyCalendarList(weeklyCalendar: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(weeklyCalendar, key = { it }) { day ->
            DayItem(day)
        }
    }
}

@Composable
fun DayItem(day: String, modifier: Modifier = Modifier) {
    val dayPart = day.substringBefore(" ")
    val hoursPart = day.substringAfter(" ", missingDelimiterValue = "Closed")
    val isActiveDay = day.length > 3

    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    if (isActiveDay) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    CircleShape
                )
        )
        Text(
            text = dayPart.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(0.2f)
        )
        Card(
//            colors = CardDefaults.cardColors(containerColor = if (isActiveDay) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = hoursPart,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
            )
        }
    }
}