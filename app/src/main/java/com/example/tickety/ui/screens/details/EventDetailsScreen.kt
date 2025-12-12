package com.example.tickety.ui.screens.details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tickety.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.AppDatabase
import model.Booking
import model.Event
import model.TicketsRepository
import model.getCurrentUserId // تأكدي من وجود هذا الـ Import
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EventDetailsScreen(navController: NavController, eventId: Int) { // ❌ تم حذف userId من هنا

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val db = remember { AppDatabase.getDatabase(context, CoroutineScope(Dispatchers.IO)) }
    val repo = remember { TicketsRepository(db.eventDao(), db.bookingDao()) }

    var event by remember { mutableStateOf<Event?>(null) }
    var quantity by remember { mutableStateOf(1) } // متغير للعدد

    // تحميل الحدث
    LaunchedEffect(eventId) {
        event = repo.getEventById(eventId)
    }

    if (event == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // ✅ جلب الـ User ID من الذاكرة
    val currentUserId = getCurrentUserId(context)

    // التحقق من الحجز السابق لهذا المستخدم تحديداً
    val bookings by repo.getAllBookings(currentUserId).collectAsState(initial = emptyList())
    val existingBooking = bookings.find { it.eventId == event!!.id }
    val isBooked = existingBooking != null

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

        // معلومات الحدث
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.DateRange, contentDescription = null, tint = Color(0xFF6650a4))
            Spacer(modifier = Modifier.width(8.dp))
            Text(event!!.date, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF6650a4))
            Spacer(modifier = Modifier.width(8.dp))
            Text(event!!.location, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CurrencyPound, contentDescription = null, tint = Color(0xFF6650a4))
            Spacer(modifier = Modifier.width(8.dp))
            Text("${event!!.price} EGP", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Description:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(event!!.description, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ واجهة اختيار العدد (تظهر فقط لو لم يتم الحجز)
        if (!isBooked) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tickets:", fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { if (quantity > 1) quantity-- }) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease")
                    }
                    Text("$quantity", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    IconButton(onClick = { quantity++ }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase")
                    }
                }
            }
            Text(
                "Total: ${event!!.price * quantity} EGP",
                modifier = Modifier.align(Alignment.End).padding(top = 4.dp),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        } else {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "You booked ${existingBooking?.quantity} ticket(s)",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // زر الحجز
        Button(
            onClick = {
                if (currentUserId == -1) {
                    Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.LoginScreen.route)
                } else {
                    scope.launch(Dispatchers.IO) {
                        if (isBooked) {
                            existingBooking?.let { repo.deleteBooking(it) }
                        } else {
                            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                            repo.insertBooking(
                                Booking(
                                    eventId = event!!.id,
                                    userId = currentUserId, // ✅ ربط الحجز باليوزر
                                    quantity = quantity,
                                    bookingDate = currentDate,
                                    totalPrice = event!!.price * quantity,
                                    eventLocation = event!!.location,
                                    eventTitle = event!!.title
                                )
                            )
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isBooked) Color.Red else MaterialTheme.colorScheme.primary
            )
        ) {
            Text(if (isBooked) "Cancel Booking" else "Confirm Booking")
        }
    }
}