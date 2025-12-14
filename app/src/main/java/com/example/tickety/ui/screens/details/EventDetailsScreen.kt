package com.example.tickety.ui.screens.details


import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Event
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CurrencyPound
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.AppDatabase
import model.Booking
import model.TicketsRepository
import model.User


@Composable
fun EventDetailsScreen(navController: NavController, eventId: Int, currentUser: MutableState<User?>) {

    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context, CoroutineScope(Dispatchers.IO)) }
    val repo = remember { TicketsRepository(db.eventDao(), db.bookingDao()) }

    var event by remember { mutableStateOf<Event?>(null) }

    // Load event from database
    LaunchedEffect(eventId) {
        event = repo.getEventById(eventId)
    }

    if (event == null) {
        // show loading indicator while fetching
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // collect bookings live
    val bookings by repo.getAllBookings((currentUser.value?.id ?: 0L).toInt()).collectAsState(initial = emptyList())

    //val isBooked = bookings.any { it.eventId == event!!.id }
    val isBooked by remember(bookings) {
        derivedStateOf { bookings.any { it.eventId == event!!.id } }
    }


    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)

    ) {

        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = event!!.imageUrl),
            contentDescription = "Event Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = event!!.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Icon(Icons.Default.DateRange, contentDescription = "Date", tint = Color(0xFF6650a4), modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(event!!.date, style = MaterialTheme.typography.headlineSmall, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = Color(0xFF6650a4), modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(event!!.location, style = MaterialTheme.typography.headlineSmall, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Icon(Icons.Default.CurrencyPound, contentDescription = "Price", tint = Color(0xFF6650a4), modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("${event!!.price} EGP", style = MaterialTheme.typography.headlineSmall, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Description:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(event!!.description, style = MaterialTheme.typography.bodyLarge, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // Book button
        Button(
            onClick = {

                coroutineScope.launch {
                    if (isBooked) {
                        val booking = bookings.find { it.eventId == event!!.id }
                        booking?.let { repo.deleteBooking(it) }
                    } else {
                        repo.insertBooking(
                            Booking(
                                eventId = event!!.id,
                                userId = currentUser.value?.id ?: 0,
                                quantity = 1,
                                bookingDate = "2025-12-11",
                                totalPrice = event!!.price,
                                eventLocation = event!!.location,
                                eventTitle = event!!.title
                            )
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(if (isBooked) "Cancel Booking" else "Book Event")
        }
    }
}
