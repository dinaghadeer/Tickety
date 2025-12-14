package com.example.tickety.ui.screens.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Event
import com.example.tickety.ui.components.EventCard
import com.example.tickety.R
import androidx.compose.ui.tooling.preview.Preview
import com.example.tickety.navigation.*
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import com.example.tickety.ui.theme.Purple40


@Composable
fun EventsScreen( navController: NavController ) {


    val events = listOf(
        Event(1, "Music Night", "12 Dec - 8 PM", "Cairo Opera House", 500.0, "Music", R.drawable.image1),
        Event(2, "Tech Summit", "20 Dec - 10 AM", "Smart Village", 700.0, "Tech", R.drawable.image2),
        Event(3, "Food Festival", "5 Jan - 5 PM", "Al Rehab Park", 200.0, "Food", R.drawable.image3)
    )

    // Search text
    var searchText by remember { mutableStateOf("") }

    // Filter events
    val filteredEvents = events.filter { event ->
        event.title.contains(searchText, ignoreCase = true) ||
                event.location.contains(searchText, ignoreCase = true)
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            Text(
                text = "Events",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(
                    top = 32.dp, start = 16.dp, end = 16.dp, bottom = 8.dp
                )
            )
        }

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            // Search bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search events") },
                modifier = Modifier
                    .fillMaxWidth(0.90f)   //  90% of screen width
                    .padding(start = 8.dp),

                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Purple40,    // your theme purple
                    unfocusedBorderColor = Purple40
                ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredEvents) { event ->
                EventCard(
                    event = event,
                    title = event.title,
                    date = event.date,
                    location = event.location,
                    price = event.price,
                    image = event.imageUrl,
                    onDetailsClick = {
                        navController.navigate(Screen.EventDetailsScreen.route)
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewEventsScreen() {
    val navController = rememberNavController()
    EventsScreen(navController = navController)
}