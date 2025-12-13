package com.example.tickety.ui.screens.mybookings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.Booking
import com.example.tickety.ui.components.BookingCard


@Composable
fun MyBookingsScreen(navController: NavController) {
    // Store the bookings in UI state
    var bookings by remember { mutableStateOf(sampleBookings()) }

    // Function to delete a booking
    fun deleteBooking(booking: Booking) {
        bookings = bookings.filter { it.bookingId != booking.bookingId }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "My Bookings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top=32.dp, start=16.dp, end=16.dp, bottom=8.dp)
        )
        val padding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        LazyColumn(modifier = Modifier.padding(padding)) {

            if (bookings.isEmpty()) {
                item {
                    Text(
                        text = "No bookings yet",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            items(bookings) { booking ->
                BookingCard(
                    booking = booking,
                    onDelete = { deleteBooking(booking) }
                )
            }
        }
    }
}

// Sample data for preview
fun sampleBookings(): List<Booking> {
    return listOf(
        Booking(1, 1, 2, "2023-12-10",200.0,"El-Alemin","Amr Diab Concert"),
        Booking(2, 2, 3, "2023-11-25",400.0,"Cairo Staduim","El-Ahly vs Zamalek"),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMyBookingsScreen() {
    MyBookingsScreen(navController = NavController(LocalContext.current))
}