package com.example.tickety.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tickety.R
import model.Booking



@Composable
fun BookingCard(
    booking: Booking,
    onDelete: () -> Unit
) {
    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFF121212) // very dark grey
//        ),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF6750A4))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(booking.eventTitle, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Date",
                    tint = Color(0xFF6650a4),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${booking.bookingDate} ",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp
                    //color = Color.DarkGray
                )
            }
            //Text(booking.bookingDate, style = MaterialTheme.typography.titleMedium)
            //Text(booking.eventLocation, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color(0xFF6650a4),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${booking.eventLocation} ",
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text("Seats: ${booking.quantity}")
            Text("Total: ${booking.totalPrice} EGP")

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
            ) {
                Text("Cancel Booking")
            }
        }
    }
}


// Preview function with sample data
@Preview(showBackground = true)
@Composable
fun BookingCardPreview() {
    MaterialTheme {

        val sampleBooking = Booking(
            eventId = 1,
            eventTitle = "Summer Music Festival 2024",
            bookingDate = "2024-07-15",
            eventLocation = "Cairo Opera House",
            quantity = 2,
            totalPrice = 500.0

        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {
            BookingCard(
                booking = sampleBooking,
                onDelete = { println("Delete clicked") }
            )
        }
    }
}