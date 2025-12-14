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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import model.Booking
import com.example.tickety.ui.components.BookingCard
import com.example.tickety.viewmodel.AppViewModelFactory
import com.example.tickety.viewmodel.BookingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.AppDatabase
import model.BookingDao
import model.TicketsRepository
import model.User


@Composable
fun MyBookingsScreen(navController: NavController, currentUser: MutableState<User?>) {

    val context = LocalContext.current

    // Database + Repository
    val db = remember { AppDatabase.getDatabase(context, CoroutineScope(Dispatchers.IO)) }
    val repo = remember { TicketsRepository(db.eventDao(), db.bookingDao()) }

    // collect current bookings
    val bookings by repo.getAllBookings((currentUser.value?.id ?: 0L).toInt()).collectAsState(initial = emptyList())
    // debug line
    LaunchedEffect(bookings) {
        println("Bookings list for user ${currentUser.value?.id}: $bookings")
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "My Bookings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.padding(16.dp)) {

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
                    onDelete = {
                        CoroutineScope(Dispatchers.IO).launch {
                            repo.deleteBooking(booking)
                        }
                   }
                )
            }
        }
    }
}
