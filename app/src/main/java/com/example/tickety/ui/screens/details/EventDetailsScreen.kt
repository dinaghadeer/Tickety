package com.example.tickety.ui.screens.details


import android.R.attr.navigationIcon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.Event
import com.example.tickety.ui.components.EventCard
import androidx.compose.ui.tooling.preview.Preview
import com.example.tickety.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CurrencyPound
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun EventDetailsScreen(navController: NavController, event: Event) {

    // Local UI state
    var isBooked by remember { mutableStateOf(false) }
//    var isFavorite by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
//            .background(
//                color = Color(0xFFF5F5F5),
//                shape = RoundedCornerShape(20.dp) // Rounded corners
//            )
//
            .padding(16.dp)

    ) {

        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = event.imageUrl),
            contentDescription = "Event Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp) // image size
                .clip(RoundedCornerShape(12.dp)), // Rounded corners
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${event.title} ",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date",
                tint = Color(0xFF6650a4),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${event.date} ",
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 16.sp
                    //color = Color.DarkGray
                )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color(0xFF6650a4),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${event.location} ",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 16.sp
        )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Icon(
                imageVector = Icons.Default.CurrencyPound,
                contentDescription = "Price",
                tint = Color(0xFF6650a4),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${event.price}  EGP",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 16.sp
        )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Description: ",
            style = MaterialTheme.typography.headlineSmall ,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text(
            text = "${event.description} ",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp
            //color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Book button
        Button(
            onClick = { isBooked = !isBooked },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(if (isBooked) "Cancel Booking" else "Book Event", style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))
        }

        // Favorite button
//        Button(
//            onClick = { isFavorite = !isFavorite },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(if (isFavorite) "Remove from Favorites" else "Add to Favorites")
//        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isBooked) {
            Text(
                text = "You have booked this event.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
        }

//        if (isFavorite) {
//            Text(
//                text = "This event is in your favorites.",
//                textAlign = TextAlign.Center,
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
    }

// Sample event for preview
fun sampleEvent() = Event(
    id = 1,
    title = "Amr Diab Concert",
    date = "12 Dec 2025",
    location = "El-Alemin",
    price = 200.0,
    description = "An amazing concert you shouldn't miss!",
    imageUrl = R.drawable.image1
)

@Preview(showBackground = true)
@Composable
fun PreviewEventDetailsScreen() {
    val navController = rememberNavController()
    EventDetailsScreen(navController = navController ,event = sampleEvent())
}
